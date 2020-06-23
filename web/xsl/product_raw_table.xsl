<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <table border="1">
            <tr>
                <th>STT</th>
                <th>Name</th>
                <th>Price</th>
                <th>Orgininal Link</th>
                <th>Image</th>
                <th>ID</th>
                <th>ParentId</th>

            </tr>
            <xsl:apply-templates />
        </table>
    </xsl:template>
    <xsl:template match="product">
        <tr>
            <td>
                <xsl:number level="single" count="product"/>         
            </td>
            <td width="300">
                <xsl:value-of select="name"/>
            </td>
            <td>
             
                    <xsl:value-of select='format-number(price, "###,###")'/>

            </td>
            <td>
                <a href="{originalLink}" target="_blank">Link gốc</a>
            </td>
            <td>
                <image width="150" src="{image}"/>
            </td>
            <td>
                <xsl:value-of select="id"/>
            </td>
            <td>
                <xsl:value-of select="parentId"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
