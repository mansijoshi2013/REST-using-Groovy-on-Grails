
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Welcome to the Gumball Machine</title>
    <style type="text/css">
table {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table th {
	background:#b5cfd2 url('cell-blue.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}
table td {
	background:#dcddc0 url('cell-grey.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}
</style>
</head>

<body>
<h1 align="center">Welcome to the Gumball Machine</h1>

<!-- FORM SECTION -->
<form name="form1" method="post" action="">

<table align = "center">
<tr><th>Serial No</th><th>Model No</th><th>Count</th>
<g:each in = "${flash.gumballs}" var="gumball">
<tr><td><a href="http://localhost:8090/GrailsGumballMachineVer1/machine/${gumball.serialNumber}">${gumball.serialNumber}</a></td><td>${gumball.modelNumber }</td><td>${gumball.countGumballs}</td></tr>
</g:each>
</table>


</form>
<!-- END FORM SECTION -->

</body>
</html>