<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : paging.xsl
    Created on : June 13, 2020, 11:07 PM
    Author     : Tran Dong
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="page">
        <ul class="page">
            <xsl:if test="current > 1">
                <li>
                    <a href="?page={current - 1}">Previous</a>
                </li>
            </xsl:if>
            
            <xsl:variable name="c" select="current" />

            <xsl:for-each select="loop">
                <xsl:choose>
                    <xsl:when test=". = $c">
                        <li>
                            <a class="success" >
                                <xsl:value-of select="."/>
                            </a>
                        </li>
                    </xsl:when>
                    <xsl:otherwise>
                        <li>
                            <a href="?page={.}">
                                <xsl:value-of select="."/>
                            </a>
                        </li>

                    </xsl:otherwise>
                </xsl:choose>

            </xsl:for-each>
            <xsl:if test="current &lt; max">
                <li>
                    <a href="?page={current+1}">Next</a>
                </li>
            </xsl:if>
           
        </ul>
    </xsl:template>

</xsl:stylesheet>
