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
            <!--            <xsl:if test="price != -1">
                <p> <span class="success">
                        <xsl:value-of select='format-number(price, "###,###")'/> VNĐ</span>
                </p>

            </xsl:if>
            <xsl:if test="price = -1">
                <p class="warning">Giá liên hệ</p>
            </xsl:if>-->
            
            <p> 
                <span class="success">
                    Độ phân giải: <xsl:value-of select='dpg'/> Megapixel</span>
            </p>
            <p> 
                <span class="success">
                    ISO trung bình: <xsl:value-of select='format-number(iso, "###,###")'/>
                </span>
            </p>
            <p> 
                <span class="success">
                    Màn chập: <xsl:value-of select='format-number(fps, "###,###")'/> FPS
                </span>
            </p>
            <button class="selectedBtn" onclick="selected({id})">Chọn để so sánh</button>
        </div>
    </xsl:template>

</xsl:stylesheet>
