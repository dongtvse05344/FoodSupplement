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
                <th>DPG</th>
                <th>ISO</th>
                <th>FPS</th>
                <th>Q DPG</th>
                <th>Q ISO</th>
                <th>Q FPS</th>
                <th>Edit</th>

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
                <xsl:if test="price != -1">
                    <xsl:value-of select='format-number(price, "###,###")'/> VNĐ
                </xsl:if>
                <xsl:if test="price = -1">
                    <p class="danger">Chưa cào được</p>
                </xsl:if>
            </td>
            <td>
                <a href="{originalLink}" target="_blank">Link gốc</a>
            </td>
            <td>
                <image width="150" src="{image}"/>
            </td>
            <td>
                <xsl:value-of select="dpg"/>
            </td>
            <td>
                <xsl:value-of select="iso"/>
            </td>
            <td>
                <xsl:value-of select="fps"/>
            </td>
            <td>
                <xsl:value-of select="QDpg"/>
            </td>
            <td>
                <xsl:value-of select="QIso"/>
            </td>
            <td>
                <xsl:value-of select="QFps"/>
            </td>
            <td>
                <a href="EditProductServlet?id={id}">Edit</a>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
