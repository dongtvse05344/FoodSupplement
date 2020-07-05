<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <xsl:variable name="max" select="products/product[last()]" />
        <table border="1" class="comparison">
            <tr>
                <th>Ảnh</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <td>
                            <img src="{image}"/>
                        </td>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Tên</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <td>
                            <xsl:value-of select="name" />
                        </td>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Độ phân giải</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <xsl:choose>
                            <xsl:when test="dpg = $max/dpg">
                                <td class="success bold">
                                    <xsl:value-of select="dpg" />
                                </td>
                            </xsl:when>
                            <xsl:otherwise>
                                <td>
                                    <xsl:value-of select="dpg" />
                                </td>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>ISO</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <xsl:choose>
                            <xsl:when test="iso = $max/iso">
                                <td class="success bold">
                                    <xsl:value-of select='format-number(iso, "###,###")' />
                                </td>
                            </xsl:when>
                            <xsl:otherwise>
                                <td>
                                    <xsl:value-of select='format-number(iso, "###,###")' />
                                </td>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Tốc độ màn chập</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <xsl:choose>
                            <xsl:when test="fps = $max/fps">
                                <td class="success bold">
                                    <xsl:value-of select="fps" />
                                </td>
                            </xsl:when>
                            <xsl:otherwise>
                                <td>
                                    <xsl:value-of select="fps" />
                                </td>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Màn hình</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <xsl:choose>
                            <xsl:when test="display = $max/display">
                                <td class="success bold">
                                    <xsl:value-of select="display" /> "
                                </td>
                            </xsl:when>
                            <xsl:otherwise>
                                <td>
                                    <xsl:value-of select="display" /> "
                                </td>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Giá</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <xsl:choose>
                            <xsl:when test="price = $max/price">
                                <td class="success bold">
                                    <xsl:choose>
                                        <xsl:when test="price > 0">
                                            <xsl:value-of select='format-number(price, "###,###")' /> VNĐ
                                        </xsl:when>
                                        <xsl:otherwise>
                                            Giá liên hệ 
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                            </xsl:when>
                            <xsl:otherwise>
                                <td>
                                    <xsl:choose>
                                        <xsl:when test="price > 0">
                                            <xsl:value-of select='format-number(price, "###,###")' /> VNĐ
                                        </xsl:when>
                                        <xsl:otherwise>
                                            Giá liên hệ 
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Chi tiết</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <td>
                            <a href="ProductDetailServlet?id={id}">Chi tiết</a>
                        </td>
                    </xsl:if>
                </xsl:for-each>
            </tr>
            <tr>
                <th>Xóa khỏi so sánh</th>
                <xsl:for-each select="products/product">
                    <xsl:if test="position() != last()">
                        <td>
                            <button onclick="removeId({id})">x</button>
                        </td>
                    </xsl:if>
                </xsl:for-each>
            </tr>
        </table>
    </xsl:template>
   
</xsl:stylesheet>
