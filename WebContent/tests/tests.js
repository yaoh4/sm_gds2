QUnit.test( "hello test", function( assert ) {

  assert.ok( 1 == "1", "Passed!" );

});


QUnit.test("setStatusIcon", function(assert) {
	var fixture = $("#qunit-fixture");
	fixture.append("<input type=\"hidden\" value=\"COMPLETED\" id=\"icReg0\"/><div id=\"icDiv0\" class=\"searchProgress\"><img src=\"../images/inprogress.png\" alt=\"In Progress\" title=\"In Progress\"/></div>");
	setStatusIcon("icReg0", "icDiv0");
	var result = $("#icDiv0").first().html().includes("../images/complete.png");
	assert.equal(result, true, "Status set to Completed");
	
	fixture.empty();
	fixture.append("<input type=\"hidden\" value=\"NOTSTARTED\" id=\"icReg0\"/><div id=\"icDiv0\" class=\"searchProgress\"><img src=\"../images/inprogress.png\" alt=\"In Progress\" title=\"In Progress\"/></div>");
	setStatusIcon("icReg0", "icDiv0");
	var result = $("#icDiv0").first().html().includes("../images/pending.png");
	assert.equal(result, true, "Status set to Not Started");
	
	fixture.empty();
	fixture.append("<input type=\"hidden\" value=\"INPROGRESS\" id=\"icReg0\"/><div id=\"icDiv0\" class=\"searchProgress\"><img src=\"../images/complete.png\" alt=\"In Progress\" title=\"In Progress\"/></div>");
	setStatusIcon("icReg0", "icDiv0");
	var result = $("#icDiv0").first().html().includes("../images/inprogress.png");
	assert.equal(result, true, "Status set to In Progress");
});

