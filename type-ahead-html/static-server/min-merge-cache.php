<?php

/* 
 Spark - Simple and Effective.
 SMART STATIC FILE SERVER : Compression / Server-side cache / Client-side caching rules
 This is a specific version for minifying and merging files (JS and CSS only) for the Spark layout.
 jonathan+dev@maddim.com
 Rev 1.5


# ABOUT EXPIRES HEADER
	When using far future expires header for static files, remeber that you need to RENAME any file that you edit to allow the users to get the new version.
	For explanations see :
	http://developer.yahoo.com/performance/rules.html#expires
	http://code.google.com/speed/page-speed/docs/caching.html#LeverageBrowserCaching

# ABOUT FILE COMPRESSION
	For best performance it is recommended to not compress a static file each time it is called by users, but to keep the compressed version in cache.
	Compression on the fly is not a good solution for static files, this is true either if you use mode deflate through Apache or zlib through PHP: you need to use a static file server with a smart caching system!
	Please visite the following links for more details:
	http://developer.yahoo.com/performance/rules.html#gzip
	http://code.google.com/speed/page-speed/docs/payload.html#GzipCompression

# ABOUT ETAG
	Note that etags has the exact same purpose than "Last-Modified" header, which has the same browser support as etags. 
	The difference between "Last-Modified" and "Etag" headers is that for the first one it is necessarily a date, when for Etag it can be anything (most commonly a combination of the file HASH, size and last modified date).
	According to Yahoo! and Google, it is redundant to have both Etag and Last-Modified headers, and removing one of them will reduce the size of the HTTP headers.
	« Removing the ETag reduces the size of the HTTP headers in both the response and subsequent requests » said Steve Souders from Yahoo!.
	Both Yahoo! and Google have disabled Etags for their main site. They're using Last-Modified HTTP header instead.
	More information :
	http://developer.yahoo.net/blog/archives/2007/07/high_performanc_11.html
	http://code.google.com/speed/page-speed/docs/caching.html#LeverageBrowserCaching
	http://httpd.apache.org/docs/2.2/mod/core.html#fileetag


SMART STATIC FILE SERVER is based on the official protocols standards of the W3C. References :

Cache-Control		: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9
Expires				: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21

Etag				: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19
Last-Modified		: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29

If-Modified-Since	: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.25
If-None-Match 		: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.26

Accept-Encoding		: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3
Vary 				: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.44

Content-Encoding	: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11
Content-Length		: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13
Content-Type		: http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17


See also 			: http://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html
and more generaly 	: http://www.w3.org/Protocols/rfc2616/rfc2616.html


Any question or comment, please contact me at jonathan+dev@maddim.com

*/

// Get the list of requested files:
$aFiles = explode(',', $_GET['files']);
$sFormat = strtolower($_GET['format']);

// Resulting in one merged file:
$resulting_file_name = md5($_GET['files']);
$resulting_file =  'cache/merged-'.$sFormat.'/'.$resulting_file_name.'.'.$sFormat; // Regular file
$resulting_file_gz = 'cache/merged-'.$sFormat.'/'.$resulting_file_name.'.gz.'.$sFormat; // Gzipped file

$mimetypes = array(
	'css' 	=> 	'text/css; charset=utf-8',
	'js' 	=> 	'application/x-javascript; charset=utf-8',
);

function gzipAccepted()
{
/* 
 According to the Apache documentation, Netscape Navigator 4.x cannot handle compression of types other than text/html.
 The versions 4.06, 4.07 and 4.08 also have problems with decompressing html files.
 See http://httpd.apache.org/docs/2.0/mod/mod_deflate.html#recommended

 Since Netscape 4.x is considered as dead (0.0% of market share), I've decided to remove this.
 If you think that you'll have Netscape 4.x users, then uncomment this:

	if(strpos($_SERVER['HTTP_USER_AGENT'], 'Mozilla/4.0') && !strpos($_SERVER['HTTP_USER_AGENT'], 'compatible'))
	{
		return false;
	}
*/
	if(strpos($_SERVER['HTTP_ACCEPT_ENCODING'], 'gzip,') !== false)
	{
		return true;
	}
	
	if(!isset($_SERVER['HTTP_ACCEPT_ENCODING']))
	{
		return false;
	}
	
	if(preg_match('@(?:^|,)\\s*((?:x-)?gzip)\\s*(?:$|,|;\\s*q=(?:0\\.|1))@', $_SERVER['HTTP_ACCEPT_ENCODING']))
	{ // Regex by Stephen Clay <steve@mrclay.org> — New BSD License
		return true;
	}
	
	return false;
}


if (isset($_SERVER['HTTP_IF_MODIFIED_SINCE']) || isset($_SERVER['HTTP_IF_NONE_MATCH'])) {

	header('HTTP/1.1 304 Not Modified'); // A specified URL is never modified (see Yahoo! performance rules), you must rename your files.
	exit;
}


if(!gzipAccepted()) { // Gzip not supported by the browser
	if(!file_exists($resulting_file))
	{
		// Minified-Merged file doesn't exist yet for the requested list
		require_once 'Minifier/Minifier.class.php';
		$minified_output = Minifier::Minify($sFormat, $aFiles);
	}

	// Create destionation directory if it doesn't exist
	$dir = dirname($resulting_file);
	if(!file_exists($dir)) {
		mkdir($dir, 0755, true);
	}
	
	file_put_contents($resulting_file, $minified_output); // Create the original file

	// Deliver the file
	header('Expires: '.gmdate('D, d M Y H:i:s', time()+315360000).' GMT');
	header('Cache-Control: max-age=315360000');
	header('Last-Modified: '.gmdate('D, d M Y H:i:s', filemtime($resulting_file)).' GMT');
//	header('Etag: '.md5_file($resulting_file));
	header('Content-Type: '.$mimetypes[$sFormat]);
	header('Content-Length: '.filesize($resulting_file));
	readfile($resulting_file);
	exit;
}

if(!file_exists($resulting_file_gz))
{
	if(!file_exists($resulting_file))
	{
		// Minified-Merged file doesn't exist yet for the requested list
		require_once 'Minifier/Minifier.class.php';
		$minified_output = Minifier::Minify($sFormat, $aFiles);
	}

	// Create destionation directory if it doesn't exist for both the original file and the gzipped cached version
	$dir = dirname($resulting_file);
	if(!file_exists($dir)) {
		mkdir($dir, 0755, true);
	}

	$dir_cached = dirname($resulting_file_gz);
	if(!file_exists($dir_cached)) {
		mkdir($dir_cached, 0755, true);
	}

	
	file_put_contents($resulting_file, $minified_output); // Create the original file
	file_put_contents($resulting_file_gz, gzencode($minified_output)); // + a gzipped version as this is what we deliver in this case
}

	if ($sFormat !== 'html' && $sFormat !== 'htm') {
		header('Expires: '.gmdate('D, d M Y H:i:s', time()+315360000).' GMT');
		header('Cache-Control: max-age=315360000');
	}
	header('Last-Modified: '.gmdate('D, d M Y H:i:s', filemtime($resulting_file)).' GMT');
//	header('Etag: '.md5_file($resulting_file));
	header('Vary: Accept-Encoding');
	header('Content-Encoding: gzip');
	header('Content-Type: '.$mimetypes[$sFormat]);
	header('Content-Length: '.filesize($resulting_file_gz));
	readfile($resulting_file_gz);
	exit;

?>