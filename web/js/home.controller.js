function loadXMLDoc(filename)
{
    if (window.ActiveXObject)
    {
        xhttp = new ActiveXObject("Msxml2.XMLHTTP");
    } else
    {
        xhttp = new XMLHttpRequest();
    }
    xhttp.open("GET", filename, false);
    try {
        xhttp.responseType = "msxml-document"
    } catch (err) {
    } // Helping IE11
    xhttp.send("");
    return xhttp.responseXML;
}
function displayResult()
{
    xmlDpg = loadXMLDoc("http://localhost:8080/FoodSupplement/TopProductDpg");
    xmlIso = loadXMLDoc("http://localhost:8080/FoodSupplement/TopProductIso");
    xmlFps = loadXMLDoc("http://localhost:8080/FoodSupplement/TopProductFps");

    xsl = loadXMLDoc("http://localhost:8080/FoodSupplement/xsl/product_item.xsl");
// code for IE
    if (window.ActiveXObject || xhttp.responseType == "msxml-document")
    {
        ex = xmlDpg.transformNode(xsl);
        document.getElementById("topDpg").innerHTML = ex;
        
        ex = xmlIso.transformNode(xsl);
        document.getElementById("topIso").innerHTML = ex;
        
        ex = xmlFps.transformNode(xsl);
        document.getElementById("topFps").innerHTML = ex;
    }
// code for Chrome, Firefox, Opera, etc.
    else if (document.implementation && document.implementation.createDocument)
    {
        xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(xsl);
        
        resultDocument = xsltProcessor.transformToFragment(xmlDpg, document);
        document.getElementById("topDpg").appendChild(resultDocument);
        
        resultDocument = xsltProcessor.transformToFragment(xmlIso, document);
        document.getElementById("topIso").appendChild(resultDocument);
        
        resultDocument = xsltProcessor.transformToFragment(xmlFps, document);
        document.getElementById("topFps").appendChild(resultDocument);
    }
}