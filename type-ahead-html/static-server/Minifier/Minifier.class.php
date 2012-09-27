<?php
/*
	Spark - Simple and Effective
	Part from Spark R6
	Any issue please contact jonathan+dev@maddim.com
*/

class Minifier
{	
	public function __construct() {
	
	}

	/*
		Main public static function.
		
		$format : 'css' or 'js'
		$aFiles : an array of one or more files.
	*/	
	public static function Minify($format, $aFiles)
	{
		$sOutput = '';
		
		if ($format == 'css') {
			require_once 'Processors/cssmin.class.php';
			
			if (is_array($aFiles)) {
				foreach ($aFiles as $file) {
					$file = './../'.$format.'/'.$file;
					if (substr($file, -8) == '.min.css' || substr($file, -15) == '.nomin.css') { // Don't minify if name ends with .min.css or .nomin.css
						$sOutput.= file_get_contents($file);
						
					} else {
						$sOutput.= cssmin::minify(file_get_contents($file));
					}
				}

			}
		
		
		} else if ($format == 'js') {
			require_once 'Processors/jsminplus.class.php';
			
			if (is_array($aFiles)) {
				foreach ($aFiles as $file) {
					$file = './../'.$format.'/'.$file;
					if (substr($file, -7) == '.min.js' || substr($file, -14) == '.nomin.js') {
						$sOutput.= file_get_contents($file);
						
					} else {
						$sOutput.= JSMinPlus::minify(file_get_contents($file));
					}
				}

			}
		
		} else {
			throw new Exception('Incorrect file format.');
			
		}

		return $sOutput;
	}

}
