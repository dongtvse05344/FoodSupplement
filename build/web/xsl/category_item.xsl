<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:p="http://xml.dongtv.vn/schema/products"
>
    <xsl:output method="html"/>

    <xsl:template match="/">
        <ul class="list-cate">
            <xsl:apply-templates/> 
        </ul>
    </xsl:template>
    <xsl:template match="p:category">
        <li>
            <a class="category_item" href="SearchServlet?cateId={p:id}">
                <xsl:value-of select="p:name"/> 
            </a>
        </li>
    </xsl:template>
</xsl:stylesheet>
