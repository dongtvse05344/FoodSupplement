<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="product">
            ID <input name="id" value="{id}" readonly="true"/>            
            <br/>
            Name: <input name="name" value="{name}"/>
            <br/>
            Price: <input name="price" value="{price}"/>
            <br/>
            DPG: <input name="dpg" value="{dpg}"/>
            <br/>
            ISO: <input name="iso" value="{iso}"/>
            <br/>
            FPS: <input name="fps" value="{fps}"/>
            <br/>
            <img src="{image}"/> 
            <br/>
            <p>
                <xsl:value-of select="description" />
            </p>
            <input type="submit" value="Edit"/>
    </xsl:template>

</xsl:stylesheet>
