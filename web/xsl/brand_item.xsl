<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : brand_item.xsl
    Created on : July 7, 2020, 10:58 AM
    Author     : shuu1
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/" >
        <ul class="brands">
            <xsl:variable name="selected" select="brands/selected"/>
            <xsl:for-each select="brands/brand">
                <xsl:choose>
                    <xsl:when test=" $selected = id">
                        <li class="selected">
                            <a  >
                                <xsl:value-of select="name"/>
                            </a>
                        </li>
                    </xsl:when>
                    <xsl:otherwise>
                        <li>
                            <a  href="SearchServlet?type=brand&amp;value={id}">
                                <xsl:value-of select="name"/>
                            </a>
                        </li>

                    </xsl:otherwise>
                </xsl:choose>
            
            </xsl:for-each>
        </ul>
    </xsl:template>
</xsl:stylesheet>
