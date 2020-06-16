<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="product">
        <div class="product-item">
            <img height="200" src="{image}"/>
            <br/>
            <a href="ProductDetailServlet?id={id}">
                <xsl:value-of select="name" />
            </a>
            <br/>
            <p>Giá chỉ từ  <span class="success"><xsl:value-of select='format-number(price, "###,###")'/> VNĐ</span></p>
        </div>
    </xsl:template>

</xsl:stylesheet>
