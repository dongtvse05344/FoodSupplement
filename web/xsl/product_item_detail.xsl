<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="product">
        <div class="product-item">
            <img src="{image}"/>
            <a href="ProductDetailServlet?id={id}">
                <xsl:value-of select="name" />
            </a>
            <p><xsl:value-of select="description" /></p>
            <a href="{originalLink}">Chi tiết</a>
        </div>
    </xsl:template>

</xsl:stylesheet>
