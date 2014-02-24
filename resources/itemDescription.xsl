<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:w="http://www.cs.au.dk/dWebTek/2014"
                xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml"/>
    <xsl:template match="/w:document">
        <div><xsl:apply-templates select="text()|w:list|w:bold|w:italics"/></div>
    </xsl:template>
    <xsl:template match="w:list">
        <ul><xsl:apply-templates select="w:item"/></ul>
    </xsl:template>
    <xsl:template match="w:item">
        <li><xsl:apply-templates/></li>
    </xsl:template>
    <xsl:template match="w:bold">
        <b><xsl:apply-templates select="text()|w:bold|w:italics"/></b>
    </xsl:template>
    <xsl:template match="w:italics">
        <i><xsl:apply-templates select="text()|w:bold|w:italics"/></i>
    </xsl:template>
</xsl:stylesheet>