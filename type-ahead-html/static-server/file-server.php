<?php

/* 
 Spark - Simple and Effective.
 SMART STATIC FILE SERVER : Compression / Server-side cache / Client-side caching rules
 jonathan@maddim.com
 Rev 1.4


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


Any question or comment, please contact me at jonathan@maddim.com

*/


$file = '../'.$_GET['file'].'.'.$_GET['ext'];
$file_ext = strtolower($_GET['ext']);

if ($file_ext == 'html' || $file_ext == 'htm') { // Add the last-modified date for HTML files to their cached version name
	$cached_file = 'cache/'.$_GET['file'].'-'.filemtime($file).'.'.$_GET['ext'];
} else {
	$cached_file = 'cache/'.$_GET['file'].'.'.$_GET['ext'];
}

$mimetypes = array(
	'html'	=>	'text/html; charset=utf-8',
	'htm'	=>	'text/html; charset=utf-8',
	'css' 	=> 	'text/css; charset=utf-8',
	'js' 	=> 	'application/x-javascript; charset=utf-8',
	'jpg' 	=> 	'image/jpeg',
	'jpeg' 	=> 	'image/jpeg',
	'png' 	=> 	'image/png',
	'gif' 	=> 	'image/gif',
	'ico' 	=> 	'image/x-icon',
	'swf' 	=> 	'application/x-shockwave-flash',
	'flv' 	=> 	'video/x-flv'
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

	if ($file_ext == 'html' || $file_ext == 'htm') { // Browser is asking if html document has been modified since...
		if (@strtotime($_SERVER['HTTP_IF_MODIFIED_SINCE']) == filemtime($file) || trim($_SERVER['HTTP_IF_NONE_MATCH']) == md5_file($file)) {
			header("HTTP/1.1 304 Not Modified");
			exit;
		}

	} else { // For all other formats you must go through the file-renaming rule. One name = one file = one version = NEVER MODIFIED
		header('HTTP/1.1 304 Not Modified');
		exit;
	}
}


if(isset($_GET['img']) || !gzipAccepted())
{
	if(!file_exists($file))
	{
		header('HTTP/1.0 404 Not Found');
		exit;
	}

	if ($file_ext !== 'html' && $file_ext !== 'htm') {
		header('Expires: '.gmdate('D, d M Y H:i:s', time()+315360000).' GMT');
		header('Cache-Control: max-age=315360000');
	}
	header('Last-Modified: '.gmdate('D, d M Y H:i:s', filemtime($file)).' GMT');
//	header('Etag: '.md5_file($file));
	header('Content-Type: '.$mimetypes[$file_ext]);
	header('Content-Length: '.filesize($file));
	readfile($file);
	exit;
}

if(!file_exists($cached_file))
{
	if(!file_exists($file))
	{
		header('HTTP/1.0 404 Not Found');
		exit;
	}

	$dir = dirname($cached_file);
	if(!file_exists($dir))
	{
		mkdir($dir, 0755, true);
	}
	
	file_put_contents($cached_file, gzencode(file_get_contents($file)));
}

	if ($file_ext !== 'html' && $file_ext !== 'htm') {
		header('Expires: '.gmdate('D, d M Y H:i:s', time()+315360000).' GMT');
		header('Cache-Control: max-age=315360000');
	}
	header('Last-Modified: '.gmdate('D, d M Y H:i:s', filemtime($file)).' GMT');
//	header('Etag: '.md5_file($file));
	header('Vary: Accept-Encoding');
	header('Content-Encoding: gzip');
	header('Content-Type: '.$mimetypes[$file_ext]);
	header('Content-Length: '.filesize($cached_file));
	readfile($cached_file);
	exit;

?>