<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="volumes">
        <table border="1">
            <tr>
                <th>Chiều dài</th>
                <th>Chiều rộng</th>
                <th>Chiều cao</th>
                <th>Giá tiền</th>
            </tr>
            <xsl:for-each select="volume">
                <tr>
                    <td><xsl:value-of select="l" /></td>
                    <td><xsl:value-of select="w" /></td>
                    <td><xsl:value-of select="h" /></td>
                    <td><span class="success"><xsl:value-of select='format-number(price, "###,###")'/> VNĐ</span></td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

</xsl:stylesheet>
