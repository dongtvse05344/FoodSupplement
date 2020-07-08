<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:p="http://xml.dongtv.vn/schema/products"
>
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
                <th>DPG</th>
                <th>ISO</th>
                <th>FPS</th>

            </tr>
            <xsl:apply-templates />
        </table>
    </xsl:template>
    <xsl:template match="p:product">
        <tr>
            <td>
                <xsl:number level="single" count="product"/>         
            </td>
            <td width="300">
                <xsl:value-of select="p:name"/>
            </td>
            <td>
             
                <xsl:value-of select='format-number(p:price, "###,###")'/>

            </td>
            <td>
                <a href="{originalLink}" target="_blank">Link gốc</a>
            </td>
            <td>
                <image width="150" src="{p:image}"/>
            </td>
            <td>
                <xsl:value-of select="p:id"/>
            </td>
            <td>
                <xsl:value-of select="p:parentId"/>
            </td>
            <td>
                <xsl:value-of select="p:dpg"/>
            </td>
            <td>
                <xsl:value-of select="p:iso"/>
            </td>
            <td>
                <xsl:value-of select="p:fps"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
