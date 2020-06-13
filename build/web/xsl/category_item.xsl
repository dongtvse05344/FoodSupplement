<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
>
    <xsl:output method="html"/>

    <!--    <xsl:template match="/">
        <html>
            <head>
                <title>index.xsl</title>
            </head>
            <body>
            </body>
        </html>
    </xsl:template>-->
    <xsl:template match="category">
        <p class="category_item" style="color:blue">
            <xsl:value-of select="name"/> 
        </p>
    </xsl:template>
    <xsl:template match="id">
    </xsl:template>

</xsl:stylesheet>
