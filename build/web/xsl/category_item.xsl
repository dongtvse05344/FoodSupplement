<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
>
    <xsl:output method="html"/>

    <xsl:template match="/">
        <ul class="list-cate">
            <xsl:apply-templates/> 
        </ul>
    </xsl:template>
    <xsl:template match="category">
        <li>
            <a class="category_item" href="SearchServlet?cateId={id}">
                <xsl:value-of select="name"/> 
            </a>
        </li>
    </xsl:template>
    <xsl:template match="id">
    </xsl:template>

</xsl:stylesheet>
