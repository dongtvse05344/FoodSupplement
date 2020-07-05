<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="product">
        <div class="">
            <img src="{image}"/>
            <br/>
            <a href="ProductDetailServlet?id={id}">
                <xsl:value-of select="name" />
            </a>
            
            <p>
                <xsl:value-of select="description" />
            </p>
            <table>
                <tr>
                    <td>Giá : </td>
                    <td>
                        <xsl:if test="price != -1">
                            <xsl:value-of select='format-number(price, "###,###")'/> VNĐ
                        </xsl:if>
                        <xsl:if test="price = -1">
                            <p class="danger">Liên hệ</p>
                        </xsl:if> 
                    </td>
                </tr>
                <tr>
                    <td>Độ phân giải: </td>
                    <td>
                        <p class="info">
                            <xsl:value-of select="dpg"/> Megepixel</p> 
                    </td>
                </tr>
                <tr>
                    <td>ISO: </td>
                    <td>
                        <p class="info">
                            <xsl:value-of select='format-number(iso, "###,###")'/>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td>Tốc độ màn chập: </td>
                    <td>
                        <p class="info">
                            <xsl:value-of select="fps"/> FPS</p> 
                    </td>
                </tr>
                <tr>
                    <td>Màn hình: </td>
                    <td>
                        <p class="info">
                            <xsl:value-of select="display"/> "</p> 
                    </td>
                </tr>
            </table>
            <a href="{originalLink}" target="_blank">Chi tiết</a>
        </div>
    </xsl:template>

</xsl:stylesheet>
