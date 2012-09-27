<?php
/* 
	Spark – Simple and Effective 
	Rev. 6
*/


// Replace the email address with the one that should receive the contact form inquiries.
define('TO_EMAIL', 'put_your_own_email_address_here@example.com');

// === You don't need to change anything else. ===



$aErrors = array();
$aResults = array();

/* Functions */

function stripslashes_if_required($sContent) {

    if(get_magic_quotes_gpc()) {
        return stripslashes($sContent);
    } else {
        return $sContent;
    }
}

function get_current_url_path() {

	$sPageUrl = "http://".$_SERVER["SERVER_NAME"].$_SERVER["REQUEST_URI"];
	$count = strlen(basename($sPageUrl));
	$sPagePath = substr($sPageUrl,0, -$count);
	return $sPagePath;
}

function output($aErrors = array(), $aResults = array()){ // Output JSON

	$bFormSent = empty($aErrors) ? true : false;
	$aCombinedData = array(
		'bFormSent' => $bFormSent,
		'aErrors' => $aErrors,
		'aResults' => $aResults
		);
		
	header('Content-type: application/json');
    echo json_encode($aCombinedData);
	exit;
}

// Check supported version of PHP
if (version_compare(PHP_VERSION, '5.2.0', '<')) { // PHP 5.2 is required for the safety filters used in this script

	$aErrors[] = 'Unsupported PHP version. <br /><em>Minimum requirement is 5.2.<br />Your version is '. PHP_VERSION .'.</em>';
	output($aErrors);
}


if (!empty($_POST)) { // Form posted?

	// Get a safe-sanitized version of the posted data
	$sFromEmail = filter_input(INPUT_POST, 'email', FILTER_SANITIZE_EMAIL);
	$sFromName = filter_input(INPUT_POST, 'name', FILTER_SANITIZE_STRING, FILTER_FLAG_STRIP_LOW);
	$sMessage  = stripslashes_if_required($_POST['message']);
	$sMessage .= "\r\n--\r\nEmail sent from ". get_current_url_path();
	
	$sHeaders  = "From: '$sFromName' <$sFromEmail>"."\r\n";
	$sHeaders .= "Reply-To: '$sFromName' <$sFromEmail>";
	
	if (filter_var($sFromEmail, FILTER_VALIDATE_EMAIL)) { // Valid email format?
	
		$bMailSent = mail(TO_EMAIL, "New inquiry from $sFromName", $sMessage, $sHeaders);
		if ($bMailSent) {
			$aResults[] = "Message sent, thank you!";
		} else {
			$aErrors[] = "Message not sent, please try again later.";
		}

	} else {
		$aErrors[] = 'Invalid email address.';
	}
} else { // Nothing posted
	$aErrors[] = 'Empty data submited.';
}

  
output($aErrors, $aResults);




