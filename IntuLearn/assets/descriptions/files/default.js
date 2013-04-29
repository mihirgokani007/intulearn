$(document).ready(function() {
    /* REF: http://digwp.com/2009/10/clean-up-empty-elements-css3/ */
    $("p:empty").remove();
    $("section").filter(function() {
        return $(this).children().length == 0;
    }).remove();
    $("section#intro").prepend("<h3>Introduction</h3>");
    $("section#properties").prepend("<h3>Properties</h3>");
    $("section#performance").prepend("<h3>Performance</h3>");
    $("section#algorithm").prepend("<h3>The Algorithm</h3>");
    $("section#references").prepend("<h3>References</h3>");

    //$("pre.snippet").snippet("java", {menu:false,showNum:false,style:"bright",transparent:false});
    //$("pre.snippet").litelighter({style:'mystyle'});
});
