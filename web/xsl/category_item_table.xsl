<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:p="http://xml.dongtv.vn/schema/products"
>
    <xsl:output method="html"/>

    <xsl:template match="p:category">
        <tr>
            <td>
                <xsl:number level="single" count="p:category"/>         
            </td>
            <td width="300">
                <xsl:value-of select="p:name"/>
            </td>
        </tr>
        
    </xsl:template>

</xsl:stylesheet>
