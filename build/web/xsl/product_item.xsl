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
            <xsl:if test="price != -1">
                <p>Giá chỉ từ  <span class="success">
                        <xsl:value-of select='format-number(price, "###,###")'/> VNĐ</span>
                </p>

            </xsl:if>
            <xsl:if test="price = -1">
                <p class="warning">Giá liên hệ</p>
            </xsl:if>
        </div>
    </xsl:template>

</xsl:stylesheet>
