//////////////////////////////////////////////////////////////////////////////////////////////
// file: Request.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.request;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public class Request {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s)
	
	// http status codes
	public static final int http_100_continue=100;
	public static final int http_103_checkpoint=103;
	public static final int http_200_ok=200;
	public static final int http_201_created=201;
	public static final int http_202_accepted=202;
	public static final int http_203_non_authoritative=203;
	public static final int http_204_no_content=204;
	public static final int http_205_reset_content=205;
	public static final int http_206_partial_content=206;
	public static final int http_207_multi_status=207;
	public static final int http_208_already_reported=208;
	public static final int http_218_this_is_fine=218;
	public static final int http_226_im_used=226;

	public static final int http_300_multiple_choices=300;
	public static final int http_301_moved_permanently=301;
	public static final int http_302_found=302;
	public static final int http_303_see_other=303;
	public static final int http_304_not_modified=304;
	public static final int http_305_use_proxy=305;
	public static final int http_306_switch_proxy=306;
	public static final int http_307_temporary_redirect=307;
	public static final int http_308_permanent_redirect=308;

	public static final int http_400_bad_request=400;
	public static final int http_401_unauthorized=401;
	public static final int http_402_payment_required=402;
	public static final int http_403_forbidden=403;
	public static final int http_404_not_found=404;
	public static final int http_405_method_not_allowed=405;
	public static final int http_406_not_acceptable=406;
	public static final int http_407_proxy_authentication_required=407;
	public static final int http_408_request_timeout=408;
	public static final int http_409_conflict=409;
	public static final int http_410_gone=410;
	public static final int http_411_length_required=411;
	public static final int http_412_precondition_failed=412;
	public static final int http_413_payload_too_large=413;
	public static final int http_414_uri_too_long=414;
	public static final int http_415_unsupported_media_type=415;
	public static final int http_416_range_not_satisfiable=416;
	public static final int http_417_expectation_failed=417;
	public static final int http_418_im_a_teapot=418;
	public static final int http_419_page_expired=419;
	public static final int http_420_method_failure=420;
	public static final int http_421_misdirected_request=421;
	public static final int http_422_unprocessable_entity=422;
	public static final int http_423_locked=423;
	public static final int http_424_failed_dependency=424;
	public static final int http_426_upgrade_required=426;
	public static final int http_428_precondition_required=428;
	public static final int http_429_too_many_requests=429;
	public static final int http_431_request_header_fields_too_large=431;
	public static final int http_440_login_time_out=440;
	public static final int http_444_no_response=444;
	public static final int http_449_retry_with=449;
	public static final int http_450_blocked_by_windows_parental_controls=450;
	public static final int http_451_unavailable_for_legal_reasons=451;
	public static final int http_495_ssl_certificate_error=495;
	public static final int http_496_ssl_certificate_required=496;
	public static final int http_498_invalid=498;
	public static final int http_499_token_required=499;

	public static final int http_500_internal_server_error=500;
	public static final int http_501_not_implemented=501;
	public static final int http_502_bad_gateway=502;
	public static final int http_503_service_unavailable=503;
	public static final int http_504_gateway_timeout=504;
	public static final int http_505_http_version_not_supported=505;
	public static final int http_506_variant_also_negotiate=506;
	public static final int http_507_insufficient_storage=507;
	public static final int http_508_loop_detected=508;
	public static final int http_509_bandwidth_apache_web_server_limit_exceeded=509;
	public static final int http_510_not_extended=510;
	public static final int http_511_network_authentication_required=511;
	public static final int http_520_unknown_error=520;
	public static final int http_521_web_server_is_down=521;
	public static final int http_522_connection_timed_out=522;
	public static final int http_523_origin_is_unreachable=523;
	public static final int http_524_a_timeout_occurred=524;
	public static final int http_525_ssl_handshake_failed=525;
	public static final int http_526_invalid_ssl_certificate=526;
	public static final int http_527_railgun_error=527;
	public static final int http_530_origin_dns_error=530;
	public static final int http_598_network_read_timeout_error=598;
}

// file: Request.java
//////////////////////////////////////////////////////////////////////////////////////////////
