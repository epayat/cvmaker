<?xml version="1.0" encoding="utf-8"?>
<!--
  ****************************************
  ***  ATLANTIS DESIGN FO-TRANSFORMER  ***
  ****************************************
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:msxsl="urn:schemas-microsoft-com:xslt" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="msxsl">
 
  <xsl:output method="xml" indent="yes"/>
  
  <!-- Base URL for the images -->
  <xsl:variable name="ImgBase"></xsl:variable>
  <xsl:variable name="LayoutDoc" select="document('CV.Atlantis.Layout.xml')/Layout"/>
  <xsl:variable name="ResumeDoc" select="Resume"/>
  <xsl:variable name="AnonymMode">
    <xsl:choose>
      <xsl:when test="$ResumeDoc/@anonym='true'">true</xsl:when>
      <xsl:otherwise>false</xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:variable name="LangFileName" select="concat('CV.Atlantis.Lang.', $ResumeDoc/@language, '.xml')"/>
  <xsl:variable name="LangDoc" select="document($LangFileName)/Translations"/>
  <xsl:variable name="availWidth" select="($LayoutDoc/@pageWidth -($LayoutDoc/@marginLeft + $LayoutDoc/@marginRight))"/>
  <!--
  <xsl:template match="@* | node()">
      <xsl:copy>
          <xsl:apply-templates select="@* | node()"/>
      </xsl:copy>
  </xsl:template>
  -->
  
  <xsl:template name="MasterLayout">
    <xsl:comment>Defines the layout master.</xsl:comment>
    <fo:layout-master-set>
      <fo:simple-page-master master-name="first"
                             page-height="{$LayoutDoc/@pageHeight}mm"
                             page-width="{$LayoutDoc/@pageWidth}mm"
                             margin-top="{$LayoutDoc/@marginTop}mm"
                             margin-bottom="{$LayoutDoc/@marginBottom}mm"
                             margin-left="{$LayoutDoc/@marginLeft}mm"
                             margin-right="{$LayoutDoc/@marginRight}mm">

        <fo:region-body margin-top="0cm"/>
        <fo:region-before extent="0cm"/>
        <fo:region-after extent="0.5cm"/>
      </fo:simple-page-master>
    </fo:layout-master-set>    
  </xsl:template>
  
  <xsl:template name="RoundedBox">
    <!-- All Units are in millimeters -->
    <xsl:param name="height">50.00</xsl:param>
    <xsl:param name="width" select="$availWidth"/>
    <xsl:param name="stokeWidth"    ><xsl:value-of select="$LayoutDoc/RoundedBox/@strokeWidth"/></xsl:param>
    <xsl:param name="radius"        ><xsl:value-of select="$LayoutDoc/RoundedBox/@radius"/></xsl:param>
    <xsl:param name="headerWidth"   ><xsl:value-of select="$LayoutDoc/RoundedBox/@headerWidth"/></xsl:param>
    <xsl:param name="hdrStartColor" ><xsl:value-of select="$LayoutDoc/RoundedBox/@hdrStartColor"/></xsl:param>
    <xsl:param name="hdrEndColor"   ><xsl:value-of select="$LayoutDoc/RoundedBox/@hdrEndColor"/></xsl:param>
    <xsl:param name="headerAlign">left</xsl:param>
    <xsl:param name="bodyStartColor"><xsl:value-of select="$LayoutDoc/RoundedBox/@bodyStartColor"/></xsl:param>
    <xsl:param name="bodyEndColor"  ><xsl:value-of select="$LayoutDoc/RoundedBox/@bodyEndColor"/></xsl:param>
    <xsl:param name="stokeColor"    ><xsl:value-of select="$LayoutDoc/RoundedBox/@strokeColor"/></xsl:param>
    
    <fo:block height="{$height}mm" font-size="8pt" white-space-collapse="false" space-before.optimum="3mm" >
      <fo:instream-foreign-object  width="100%" >
        <svg xmlns="http://www.w3.org/2000/svg" width="{$width}mm" height="{$height}mm">
          <defs>
            <xsl:if test="$headerWidth &gt; ($radius * 4)">
              <linearGradient id="gradH" x1="0%" y1="0%" x2="0%" y2="100%">
                <stop offset="0%" style="stop-color:{$hdrStartColor};stop-opacity:1" />
                <stop offset="100%" style="stop-color:{$hdrEndColor};stop-opacity:1" />
              </linearGradient>
            </xsl:if>  
            <linearGradient id="gradB" x1="0%" y1="0%" x2="25%" y2="100%">
              <stop offset="0%" style="stop-color:{$bodyStartColor};stop-opacity:1" />
              <stop offset="100%" style="stop-color:{$bodyEndColor};stop-opacity:1" />
            </linearGradient>
          </defs>
          
          <!-- Rounded Box  -->
          <rect x="{$stokeWidth}mm" rx="{$radius}mm" ry="{$radius}mm" width="{$width - $stokeWidth * 2}mm" fill="url(#gradB)" style="stroke:{$stokeColor};stroke-width:{$stokeWidth}mm;opacity:1">
            <xsl:attribute name="y">
              <xsl:choose>
                <xsl:when test="$headerWidth &gt; ($radius * 4)"><xsl:value-of select="$radius + $stokeWidth"/>mm</xsl:when>
                <xsl:otherwise><xsl:value-of select="$stokeWidth"/>mm</xsl:otherwise>
              </xsl:choose>              
            </xsl:attribute>
            <xsl:attribute name="height">
              <xsl:choose>
                <xsl:when test="$headerWidth &gt; ($radius * 4)"><xsl:value-of select="$height - ($radius + $stokeWidth)"/>mm</xsl:when>
                <xsl:otherwise><xsl:value-of select="$height - ($stokeWidth * 2)"/>mm</xsl:otherwise>
              </xsl:choose>
            </xsl:attribute>
          </rect>  
          
          <!-- Rounded Box for Header -->
          <xsl:variable name="leftMargin">
            <xsl:choose>
              <xsl:when test="$headerAlign='left'"><xsl:value-of select="($radius * 2)"/></xsl:when>
              <xsl:when test="$headerAlign='center'"><xsl:value-of select="(($width - $headerWidth) div 2 )"/></xsl:when>
              <xsl:otherwise><xsl:value-of select="($width - ($headerWidth + ($radius * 2)))"/></xsl:otherwise>
            </xsl:choose>
          </xsl:variable>
          <xsl:if test="$headerWidth &gt; ($radius * 4)">
            <rect x="{$leftMargin}mm" y="{$stokeWidth}mm" rx="{$radius}mm" ry="{$radius}mm" width="{$headerWidth}mm" height="{$radius * 2}mm" fill="url(#gradH)" style="stroke:{$stokeColor};stroke-width:{$stokeWidth}mm;opacity:1"/>
          </xsl:if>
        </svg>
      </fo:instream-foreign-object>
    </fo:block>    
  </xsl:template>
  
  <xsl:template name="BackgroundLayerForMainPage">
    <xsl:comment> Background Layer </xsl:comment>
    <fo:block-container absolute-position="absolute" >
      <fo:table table-layout="fixed" width="100%">
        <fo:table-body>
          <fo:table-row height="{$LayoutDoc/ContactInfo/@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
            <fo:table-cell>
              <xsl:comment>ContactInfo Background Box</xsl:comment>
              <xsl:call-template name="RoundedBox">
                <xsl:with-param name="height" select="$LayoutDoc/ContactInfo/@height"/>
                <xsl:with-param name="headerWidth">0.00</xsl:with-param>
              </xsl:call-template>
            </fo:table-cell>
          </fo:table-row>

          <fo:table-row height="{$LayoutDoc/Skills/@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
            <fo:table-cell>
              <xsl:comment>Skills Background Box</xsl:comment>
              <xsl:call-template name="RoundedBox">
                <xsl:with-param name="height" select="$LayoutDoc/Skills/@height"/>
              </xsl:call-template>
            </fo:table-cell>
          </fo:table-row>
          
          <fo:table-row height="{$LayoutDoc/Fields/@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
            <fo:table-cell>
              <xsl:comment>Fields Background Box</xsl:comment>
              <xsl:call-template name="RoundedBox">
                <xsl:with-param name="height" select="$LayoutDoc/Fields/@height"/>
              </xsl:call-template>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
      </fo:table>          
    </fo:block-container>    
  </xsl:template>

  <xsl:template name="BackgroundLayerForPersonalDataSheet">
    <xsl:comment> Background Layer </xsl:comment>
    <fo:block-container absolute-position="absolute" >
      <xsl:comment>PersonalDataSheets Background Box</xsl:comment>
      <xsl:call-template name="RoundedBox">
        <xsl:with-param name="height" select="$LayoutDoc/PersonalDataSheet/@height"/>
      </xsl:call-template>
    </fo:block-container>
  </xsl:template>

  <xsl:template match="Page/CareerDetail" mode="Background">
    <fo:table-row height="{@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
      <fo:table-cell>
        <xsl:comment>CareerDetails Background Box</xsl:comment>
        <xsl:call-template name="RoundedBox">
          <xsl:with-param name="height" select="@height"/>
          <xsl:with-param name="headerAlign">center</xsl:with-param>
        </xsl:call-template>
      </fo:table-cell>
    </fo:table-row>    
  </xsl:template>
  
  <xsl:template match="Page/EducationalBackground" mode="Background">
    <fo:table-row height="{@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
      <fo:table-cell>
        <xsl:comment>CareerDetails Background Box</xsl:comment>
        <xsl:call-template name="RoundedBox">
          <xsl:with-param name="height" select="@height"/>
          <xsl:with-param name="headerAlign">center</xsl:with-param>
        </xsl:call-template>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>  
  
  <xsl:template match="CareerDetails/Page" mode="Background">
    <xsl:comment> Background Layer </xsl:comment>
    <fo:block-container absolute-position="absolute" >
      <fo:table table-layout="fixed" width="100%">
        <fo:table-body>
          <xsl:apply-templates mode="Background"/>          
        </fo:table-body>
      </fo:table>
    </fo:block-container>
  </xsl:template>

  <xsl:template match="Address">
    <xsl:if test="($AnonymMode='false')">
      <xsl:value-of select="concat(Street,' • ', PostalCode, ' • ', City, ' • ', Country)"/>
    </xsl:if>
  </xsl:template>
  
  <xsl:template name="ContactInfoContent">
    <xsl:comment>ContactInfo Content</xsl:comment>
    <fo:block>
      <fo:table table-layout="fixed" width="100%" margin-top="0.5mm">
        <fo:table-column column-width="proportional-column-width(1)"/>
        <fo:table-column column-width="35mm"/>
        <fo:table-column column-width="2mm"/>
        <fo:table-body>
          <fo:table-row height="36mm">
            <fo:table-cell padding-left="10mm">
              <fo:table table-layout="fixed" width="100%" font-family="Arial" font-size="10pt" margin-top="3mm" margin-bottom="3mm" border-width="0.00mm" border-style="solid">
                <fo:table-body>
                  <fo:table-row><fo:table-cell><fo:block padding-top="2mm"  padding-bottom="2mm" font-size="14pt" font-weight="bold"><xsl:value-of select="/Resume/Personal/Career"/></fo:block></fo:table-cell></fo:table-row>
                  <fo:table-row><fo:table-cell>
                    <fo:block padding-bottom="1.5mm" font-size="12pt"><xsl:if test="count(/Resume/Personal/Title) &gt; 0"><xsl:value-of select="/Resume/Personal/Title"/>&#160;</xsl:if>
                      <xsl:choose>
                        <xsl:when test="($AnonymMode='false')"><xsl:value-of select="/Resume/Personal/FirstName"/>&#160;<xsl:value-of select="/Resume/Personal/LastName"/></xsl:when>
                        <xsl:otherwise><xsl:value-of select="$ResumeDoc/@anonymId"/></xsl:otherwise>
                      </xsl:choose>
                    </fo:block>
                  </fo:table-cell></fo:table-row>                  
                  <fo:table-row><fo:table-cell><fo:block padding-bottom="0.5mm"><xsl:apply-templates select="/Resume/Personal/Address"/></fo:block></fo:table-cell></fo:table-row>
                  <fo:table-row><fo:table-cell><fo:block padding-bottom="0.5mm" font-weight="bold">                    
                    <xsl:choose>
                      <xsl:when test="($AnonymMode='false')">
                        <xsl:value-of select="/Resume/Personal/Phone"/>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:value-of select="$ResumeDoc/@recruiterName"/>
                      </xsl:otherwise>
                    </xsl:choose>
                  </fo:block></fo:table-cell></fo:table-row>
                  <fo:table-row><fo:table-cell><fo:block>
                    
                    <xsl:choose>
                      <xsl:when test="($AnonymMode='false')">
                        <fo:basic-link external-destination="mailto:{/Resume/Personal/Email}" text-decoration="underline" color="blue">
                          <xsl:value-of select="/Resume/Personal/Email"/>
                        </fo:basic-link>
                      </xsl:when>
                      <xsl:otherwise>
                        <fo:basic-link external-destination="mailto:{$ResumeDoc/@recruiterEmail}" text-decoration="underline" color="blue">
                          <xsl:value-of select="$ResumeDoc/@recruiterEmail"/>
                        </fo:basic-link>                        
                      </xsl:otherwise>
                    </xsl:choose>
                  </fo:block></fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:table-cell>
            <fo:table-cell vertical-align="bottom" display-align="after" >
              <fo:block display-align="after" vertical-align="bottom" height="36mm"><fo:external-graphic vertical-align="bottom"  width="100%" height="36mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{/Resume/Personal/Photo/@url}"/></fo:block>
            </fo:table-cell>
            <fo:table-cell><fo:block></fo:block></fo:table-cell>
          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block>    
  </xsl:template>

  <xsl:template match="Skill">
    <xsl:comment>*** <xsl:value-of select="Name/Text"/> ***</xsl:comment>
    <fo:table-cell>
      <fo:block padding-top="3mm">
        <fo:table table-layout="fixed" width="100%" border-width="0.00mm" border-style="solid" border-color="red">
          <fo:table-column column-width="16mm"/>
          <fo:table-column column-width="proportional-column-width(1)"/>
          <fo:table-body>
            <fo:table-row>
              <!-- Skill Icon -->
              <fo:table-cell margin="2mm"><fo:block><fo:external-graphic border-width="0.00mm" border-style="solid" width="12mm" height="12mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{Icon/@url}"/></fo:block></fo:table-cell>
              
              <!-- Skill Name/Logo -->
              <fo:table-cell margin-left="2mm">         
                <xsl:choose>
                  <xsl:when test="count(Name/Icon)&gt;0">
                    <fo:block><fo:external-graphic border-width="0.00mm" border-style="solid" width="100%" height="5mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{Name/Icon/@url}"/></fo:block>
                  </xsl:when>
                  <xsl:otherwise><fo:block margin-bottom="0.5mm" margin-top="0.5mm" font-size="11pt"><xsl:value-of select="Name/Text"/></fo:block></xsl:otherwise>
                </xsl:choose>
                
                <!-- Skill Details/Highlights -->
                <fo:block>
                  <fo:list-block font-family="Tahoma" font-size="8pt" letter-spacing="0.1pt"  start-indent="5mm">
                    <xsl:for-each select="Details/Detail">
                      <fo:list-item><fo:list-item-label><fo:block>•</fo:block></fo:list-item-label><fo:list-item-body start-indent="10mm"><fo:block><xsl:value-of select="."/></fo:block></fo:list-item-body></fo:list-item>
                    </xsl:for-each>
                  </fo:list-block>
                </fo:block>
              </fo:table-cell>
            </fo:table-row>
          </fo:table-body>
        </fo:table>
      </fo:block>
    </fo:table-cell>
  </xsl:template>

  <xsl:template name="RoundBoxContent">
    <xsl:param name="headerText"></xsl:param>
    <xsl:param name="headerAlign">left</xsl:param>
    <xsl:param name="headerWidth"><xsl:value-of select="$LayoutDoc/RoundedBox/@headerWidth"/></xsl:param>
    <xsl:param name="headerHeight"><xsl:value-of select="$LayoutDoc/RoundedBox/@radius * 2"/></xsl:param>
    <xsl:param name="spaceBefore"><xsl:value-of select="$LayoutDoc/RoundedBox/@spaceBefore"/></xsl:param>
    <xsl:param name="content"></xsl:param>
    <fo:block space-before.optimum="{$spaceBefore}mm" border-style="solid" border-width="0.0mm">
      <fo:table table-layout="fixed" width="100%" font-family="Arial" font-size="10pt" letter-spacing="0.2pt">
        <fo:table-body>
          
          <xsl:comment>Header</xsl:comment>
          <xsl:variable name="leftMargin">
            <xsl:choose>
              <xsl:when test="$headerAlign='left'"><xsl:value-of select="($LayoutDoc/RoundedBox/@radius * 2)"/></xsl:when>
              <xsl:when test="$headerAlign='center'"><xsl:value-of select="(($availWidth - $headerWidth) div 2 )"/></xsl:when>
              <xsl:otherwise><xsl:value-of select="($availWidth - ($headerWidth + ($LayoutDoc/RoundedBox/@radius * 2)))"/></xsl:otherwise>
            </xsl:choose>
          </xsl:variable>
          <fo:table-row>
            <fo:table-cell vertical-align="middle" display-align="center">
              <fo:table table-layout="fixed" width="100%" margin-top="1mm">
                <fo:table-column column-width="{$leftMargin - 1}mm"/>
                <fo:table-column column-width="{$headerWidth}mm"/>
                <fo:table-body>
                  <fo:table-row height="{$headerHeight}mm">
                    <fo:table-cell border-style="solid" border-width="0.0mm"><fo:block></fo:block></fo:table-cell>
                    <fo:table-cell border-style="solid" border-width="0.0mm"><fo:block vertical-align="middle" display-align="center" text-align="center"><xsl:value-of select="$headerText"/></fo:block></fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:table-cell>
          </fo:table-row>
          
          <xsl:comment>Content</xsl:comment>
          <fo:table-row>
            <fo:table-cell padding-left="2mm" padding-right="2mm" padding-top="0mm" padding-bottom="2mm" border-style="solid" border-width="0.00mm">
              <xsl:copy-of select="$content"/>
            </fo:table-cell>
          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block>
  </xsl:template>
  
  <xsl:template name="SkillsContent">
    <xsl:call-template name="RoundBoxContent">
      <xsl:with-param name="headerText"><xsl:value-of select="$LangDoc/Translation[@id='skillsHeader']"/></xsl:with-param>
      <xsl:with-param name="content">
        <fo:block font-size="4pt">&#160;</fo:block>
        <fo:table table-layout="fixed" width="100%" border-width="0.00mm" border-style="solid" border-color="green">
          <fo:table-column column-width="50%"/>
          <fo:table-column column-width="50%"/>
          <fo:table-body>
            <xsl:for-each select="$LayoutDoc/Skills/Rows/Row">
              <fo:table-row height="23mm" border-bottom-style="solid" border-bottom-color="#BBBBBB" >

                <xsl:attribute name="border-bottom-width">
                  <xsl:choose>
                    <xsl:when test="position()&lt;count(../Row)">0.25mm</xsl:when>
                    <xsl:otherwise>0.00mm</xsl:otherwise>
                  </xsl:choose>
                </xsl:attribute>

                <xsl:variable name="LeftId" select="LeftCol/@skillId"/>
                <xsl:variable name="RightId" select="RightCol/@skillId"/>

                <xsl:comment><xsl:value-of select="position()"/>.Row</xsl:comment>

                <xsl:apply-templates select="$ResumeDoc//Skill[@id=$LeftId]"/>
                <xsl:apply-templates select="$ResumeDoc//Skill[@id=$RightId]"/>
              </fo:table-row>
            </xsl:for-each>
          </fo:table-body>
        </fo:table>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="FieldsContent">
    <xsl:call-template name="RoundBoxContent">
      <xsl:with-param name="headerText"><xsl:value-of select="$LangDoc/Translation[@id='fieldsHeader']"/></xsl:with-param>
      <xsl:with-param name="content">
        <fo:table table-layout="fixed" width="100%" border-width="0.00mm" border-style="solid" border-color="green">
          <fo:table-column column-width="proportional-column-width(1)"/>
          <fo:table-column column-width="45mm"/>
          <fo:table-body>
            <fo:table-row>
              <fo:table-cell>
                <fo:block>&#160;</fo:block>
                <fo:block>
                  <fo:list-block font-family="Arial" font-size="10pt" letter-spacing="0.1pt"  start-indent="5mm">
                    <xsl:for-each select="$ResumeDoc/Fields/Field">
                      <fo:list-item>
                        <fo:list-item-label><fo:block>•</fo:block></fo:list-item-label>
                        <fo:list-item-body start-indent="10mm"><fo:block><xsl:value-of select="."/></fo:block></fo:list-item-body>
                      </fo:list-item>
                    </xsl:for-each>
                  </fo:list-block>
                </fo:block>
              </fo:table-cell>
              <fo:table-cell>
                <fo:block>
                  <xsl:if test="$LayoutDoc//Fields/@showImage='true'">
                    <fo:external-graphic border-width="0.00mm" border-style="solid" width="100%" height="100%" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{$LayoutDoc//Fields/@image}"/>
                  </xsl:if>
                </fo:block>
              </fo:table-cell>              
            </fo:table-row>
          </fo:table-body>
        </fo:table>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="ContentLayerForMainPage">    
    <xsl:comment>Content Layer</xsl:comment>
    <fo:block-container absolute-position="absolute" font-size="8pt" letter-spacing="0.2pt">
      <fo:table table-layout="fixed" width="100%">
        <fo:table-body>
          <fo:table-row height="{$LayoutDoc/ContactInfo/@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
            <fo:table-cell>
              <xsl:comment>ContactInfo Content</xsl:comment>
              <xsl:call-template name="ContactInfoContent"/>
            </fo:table-cell>
          </fo:table-row>

          <fo:table-row height="{$LayoutDoc/Skills/@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
            <fo:table-cell>
              <xsl:comment>Skills Content</xsl:comment>
              <xsl:call-template name="SkillsContent"/>
            </fo:table-cell>
          </fo:table-row>
          
          <fo:table-row height="{$LayoutDoc/Fields/@height + $LayoutDoc/RoundedBox/@spaceBefore}mm">
            <fo:table-cell>
              <xsl:comment>Fields Content</xsl:comment>
              <xsl:call-template name="FieldsContent"/>
            </fo:table-cell>
          </fo:table-row>          
        </fo:table-body>
      </fo:table>

    </fo:block-container>   
  </xsl:template>

  <xsl:template match="PersonalDataSheet/Item">
    <fo:table table-layout="fixed" width="100%">
      <fo:table-column column-width="30mm"/>
      <fo:table-column column-width="proportional-column-width(1)"/>
      <fo:table-column column-width="30mm"/>
      <fo:table-body>
        <fo:table-row>
          <fo:table-cell padding-left="3mm" vertical-align="middle" display-align="center" border-width="0.0mm" border-style="solid">
            <fo:block vertical-align="middle" display-align="center">
              <fo:external-graphic vertical-align="middle" display-align="center"  width="100%" height="10.5mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{@instLogo}"/>
            </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-left="7mm" padding-right="7mm" padding-top="1mm"><fo:block><xsl:value-of select="."/></fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="1mm">
            <fo:block font-weight="bold"><xsl:value-of select="concat(@startedOn, ' - ', @endedOn)"/></fo:block>
            <fo:block color="gray"><xsl:value-of select="@formOfEmp"/></fo:block>
          </fo:table-cell>          
        </fo:table-row>
      </fo:table-body>
    </fo:table>
  </xsl:template>
  
  <xsl:template name="ContentLayerForPersonalDataSheet">
    <xsl:comment>Content Layer</xsl:comment>
    <fo:block-container absolute-position="absolute" font-size="8pt">
      
        <xsl:call-template name="RoundBoxContent">
          <xsl:with-param name="headerText">
            <xsl:value-of select="$LangDoc/Translation[@id='persDataSheetHdr']"/>
          </xsl:with-param>
          <xsl:with-param name="content">            
            <fo:table table-layout="fixed" width="100%" border-width="0.00mm" border-style="solid" border-color="green" font-family="Tahoma" font-size="8pt">
              <fo:table-body>                
                <fo:table-row height="{$LayoutDoc/PersonalDataSheet/@height - (($LayoutDoc/RoundedBox/@radius * 2) + 32)}mm">
                  <fo:table-cell>
                    <fo:table table-layout="fixed" width="100%" border-width="0.00mm" border-style="solid" border-color="green" font-family="Tahoma" font-size="8pt" margin-top="4mm">
                      <fo:table-body>
                        <xsl:for-each select="$ResumeDoc/PersonalDataSheet/Item">
                          <fo:table-row height="13mm" border-bottom-style="solid" border-bottom-color="#BBBBBB" >

                            <xsl:attribute name="border-bottom-width">
                              <xsl:choose>
                                <xsl:when test="position()&lt;count(../Item)">0.20mm</xsl:when>
                                <xsl:otherwise>0.00mm</xsl:otherwise>
                              </xsl:choose>
                            </xsl:attribute>
                            <fo:table-cell padding-top="1mm" padding-bottom="0mm">
                              <xsl:apply-templates select="."/>
                            </fo:table-cell>
                          </fo:table-row>
                        </xsl:for-each>
                      </fo:table-body>
                    </fo:table>                      
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell>
                    <!-- Personal Info Section at the Bottom-->
                    <fo:table table-layout="fixed" width="100%"  border-width="0.00mm" border-style="solid" border-color="green" font-family="Tahoma" font-size="8pt" color="#222222">
                      <fo:table-column column-width="33%"/>
                      <fo:table-column column-width="34%"/>
                      <fo:table-column column-width="33%"/>
                      <fo:table-body>
                        <fo:table-row height="6mm" font-weight="bold" color="#222222">
                          <fo:table-cell text-align="center" padding-right="3mm"><fo:block padding-bottom="0.50mm" border-bottom-width="0.20mm" border-bottom-style="solid" border-bottom-color="#AAAAAA"><xsl:value-of select="$LangDoc/Translation[@id='personalInfo']"/></fo:block></fo:table-cell>
                          <fo:table-cell text-align="center"><fo:block padding-bottom="0.50mm" border-bottom-width="0.20mm" border-bottom-style="solid" border-bottom-color="#AAAAAA"><xsl:value-of select="$LangDoc/Translation[@id='languageSkills']"/></fo:block></fo:table-cell>
                          <fo:table-cell text-align="center" padding-left="3mm"><fo:block padding-bottom="0.50mm" border-bottom-width="0.20mm" border-bottom-style="solid" border-bottom-color="#AAAAAA"><xsl:value-of select="$LangDoc/Translation[@id='interests']"/></fo:block></fo:table-cell>
                        </fo:table-row>
                        <fo:table-row height="24mm">
                          <fo:table-cell text-align="center" padding-right="3mm">
                            <fo:block><xsl:value-of select="concat($ResumeDoc/Personal/Height/text(), $ResumeDoc/Personal/Height/@unit, '/', $ResumeDoc/Personal/Weight/text(), $ResumeDoc/Personal/Weight/@unit, ', ', $ResumeDoc/Personal/MaritalStatus, ', ', $LangDoc/Translation[@id='birthDate'],' ', $ResumeDoc/Personal/Birthdate)"/></fo:block>
                          </fo:table-cell>
                          <fo:table-cell padding-left="2mm" padding-right="1.50mm">
                            <fo:table table-layout="fixed" width="100%">
                              <fo:table-column column-width="10mm"/>
                              <fo:table-column column-width="proportional-column-width(1)"/>
                              <fo:table-column column-width="20mm"/>
                              <fo:table-body>
                                <xsl:for-each select="$ResumeDoc/Languages/Language">
                                  <xsl:variable name="langId" select="@id"/>
                                  <fo:table-row height="6mm">
                                    <fo:table-cell vertical-align="middle" display-align="center">
                                      <fo:block><fo:external-graphic vertical-align="middle" display-align="center"  width="100%" height="5mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}images/FLAGS.{@id}.png"/></fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell vertical-align="middle" display-align="center"><fo:block font-size="9pt" >
                                      <xsl:value-of select="$LangDoc/Translation[@id=$langId]"/></fo:block></fo:table-cell>
                                    <fo:table-cell vertical-align="middle" display-align="center">
                                      <fo:block><fo:external-graphic vertical-align="middle" display-align="center"  width="100%" height="5mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}images/Star Rating-{@level}.png"/></fo:block>
                                    </fo:table-cell>
                                  </fo:table-row>
                                </xsl:for-each>
                              </fo:table-body>
                            </fo:table>
                          </fo:table-cell>
                          <fo:table-cell text-align="left" padding-left="0mm">
                            <fo:block>
                              <fo:list-block start-indent="5mm">
                                <xsl:for-each select="$ResumeDoc/Personal/Interests/Interest">
                                  <fo:list-item>
                                    <fo:list-item-label>
                                      <fo:block>•</fo:block>
                                    </fo:list-item-label>
                                    <fo:list-item-body start-indent="10mm">
                                      <fo:block><xsl:value-of select="."/></fo:block>
                                    </fo:list-item-body>
                                  </fo:list-item>
                                </xsl:for-each>
                              </fo:list-block>
                            </fo:block>
                          </fo:table-cell>
                        </fo:table-row>                        
                      </fo:table-body>
                    </fo:table>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>            
          </xsl:with-param>
        </xsl:call-template>   
      
    </fo:block-container>
  </xsl:template>  

  <xsl:template name="PageMain">
    <xsl:call-template name="BackgroundLayerForMainPage"/>
    <xsl:call-template name="ContentLayerForMainPage"/>   
  </xsl:template>
  
  <xsl:template name="PagePersonalDataSheet">
    <xsl:call-template name="BackgroundLayerForPersonalDataSheet"/>
    <xsl:call-template name="ContentLayerForPersonalDataSheet"/>
  </xsl:template>

  <xsl:template match="Page/CareerDetail" mode="Content">
    <fo:table-row height="{@height}mm">
      <fo:table-cell>
        <xsl:variable name="id" select="@id"/>
        <xsl:variable name="CareerData" select="$ResumeDoc/CareerDetails/CareerDetail[@id=$id]"/>
        <xsl:variable name="LayoutData" select="."/>
        <xsl:variable name="headerHeight">14</xsl:variable>
        <xsl:variable name="footerHeight">22</xsl:variable>
        <xsl:comment>CareerDetails Content</xsl:comment>
        <xsl:call-template name="RoundBoxContent">
          <xsl:with-param name="headerAlign">center</xsl:with-param>
          <xsl:with-param name="headerText" select="concat($CareerData/@type,' ', count($CareerData/preceding-sibling::*)+1, ' / ', count($ResumeDoc/CareerDetails/CareerDetail))"/>
          <xsl:with-param name="content">
            <fo:table table-layout="fixed" width="100%" font-family="Tahoma" font-size="8pt" border-color="red" border-width="0.0mm" border-style="solid">
              <fo:table-column column-width="proportional-column-width(1)"/>
              <fo:table-column column-width="50mm"/>
              <fo:table-body>
                <!-- Institution Area (Header) -->
                <fo:table-row>
                  <!-- Institution Logo -->
                  <fo:table-cell number-rows-spanned="3" padding-left="3mm">
                    <fo:block><fo:external-graphic vertical-align="middle" display-align="center"  width="100%" height="{$headerHeight - 1}mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{@instLogo}"/></fo:block>
                  </fo:table-cell>
                  <!-- Start, End Dates, Duration -->
                  <fo:table-cell height="{$headerHeight div 3}mm" text-align="right" vertical-align="middle" display-align="center" padding-right="3mm" border-color="green" border-width="0.0mm" border-style="solid">
                    <fo:block font-weight="bold" >
                      <fo:inline color="#303030"><xsl:value-of select="concat($CareerData/@startedOn,' - ', $CareerData/@finishedOn)"/></fo:inline>
                      <fo:inline color="#606060">&#160;<xsl:value-of select="concat('(', $CareerData/@duration, ')')"/></fo:inline>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <!-- Field -->
                <fo:table-row height="{$headerHeight div 3}mm">
                  <fo:table-cell text-align="right" vertical-align="middle" display-align="center" padding-right="3mm" border-color="red" border-width="0.0mm" border-style="solid">
                    <fo:block color="#606060"><xsl:value-of select="concat($LangDoc/Translation[@id='fieldCaption'],': ', $CareerData/Institution/@field)"/>&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <!-- Institution Size-->
                <fo:table-row height="{$headerHeight div 3}mm">
                  <fo:table-cell vertical-align="middle" display-align="center" text-align="right" padding-right="3mm" border-color="red" border-width="0.0mm" border-style="solid">
                    <fo:block color="#606060"><xsl:value-of select="$CareerData/Institution/@size"/>&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <!-- Seperator Line -->
                <fo:table-row>
                  <fo:table-cell number-columns-spanned="2" padding-top="1mm">
                    <fo:block border-top-width="{$LayoutDoc/RoundedBox/@strokeWidth}mm" border-top-style="solid" border-top-color="{$LayoutDoc/RoundedBox/@strokeColor}"></fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <!-- Career Details Body -->
                <fo:table-row height="{@height - ($headerHeight + $footerHeight + ($LayoutDoc/RoundedBox/@radius * 2) + 8)}mm">
                  <fo:table-cell number-columns-spanned="2" border-width="0.0mm" border-style="solid" border-color="green" padding-left="2mm" padding-right="3mm" padding-top="3mm">
                    <fo:block>
                      <xsl:variable name="fontSize">
                        <xsl:choose>
                          <xsl:when test="count($LayoutData/@fontSize) = 0">10pt</xsl:when>
                          <xsl:otherwise><xsl:value-of select="$LayoutData/@fontSize"/></xsl:otherwise>
                        </xsl:choose>
                      </xsl:variable>
                      <xsl:variable name="hdrFontSize">
                        <xsl:choose>
                          <xsl:when test="count($LayoutData/@hdrFontSize) = 0">13pt</xsl:when>
                          <xsl:otherwise><xsl:value-of select="$LayoutData/@hdrFontSize"/></xsl:otherwise>
                        </xsl:choose>
                      </xsl:variable>
                      <xsl:for-each select="$CareerData/Tasks/Task">
                        <fo:block font-family="Cambria" font-size="{$hdrFontSize}" font-weight="bold" color="#4F80BD" space-before="2mm"><xsl:value-of select="Summary"/></fo:block>
                        <fo:block space-before="1mm">
                          <fo:list-block font-family="Arial" font-size="{$fontSize}" letter-spacing="0.2pt"  start-indent="5mm">
                            <xsl:for-each select="Detail">
                              <fo:list-item>
                                <xsl:if test="count($LayoutData/@lineHeight) &gt; 0">
                                  <xsl:attribute name="line-height">
                                    <xsl:value-of select="$LayoutData/@lineHeight"/>
                                  </xsl:attribute>
                                </xsl:if>
                                <fo:list-item-label>
                                  <fo:block>•</fo:block>
                                </fo:list-item-label>
                                <fo:list-item-body start-indent="10mm">
                                  <fo:block><xsl:value-of select="."/></fo:block>
                                </fo:list-item-body>
                              </fo:list-item>
                            </xsl:for-each>
                          </fo:list-block>
                        </fo:block>
                      </xsl:for-each>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <!-- Used Technologies (Footer) -->
                <fo:table-row height="{$footerHeight}mm">
                  <fo:table-cell number-columns-spanned="2" padding-left="1mm" padding-right="1mm">
                    <fo:table table-layout="fixed" width="100%" background-repeat="repeat" background-image="images/TechBck3.png">
                      <xsl:for-each select="HighligtedTechs/Technology">
                        <fo:table-column column-width="{@width + 4}mm"/>
                      </xsl:for-each>
                      <fo:table-column column-width="proportional-column-width(1)"/>
                      <fo:table-body>
                        <fo:table-row height="13mm">
                          <!-- Highlighted Technologies with icons -->
                          <xsl:for-each select="HighligtedTechs/Technology">
                            <xsl:variable name="height">
                              <xsl:choose>
                                <xsl:when test="count(@height) = 0">11</xsl:when>
                                <xsl:otherwise><xsl:value-of select="@height"/></xsl:otherwise>
                              </xsl:choose>
                            </xsl:variable>
                            <fo:table-cell column-width="{@width + 4}mm" vertical-align="bottom" display-align="after">
                              <fo:block text-align="center" vertical-align="bottom">
                                <fo:external-graphic border-color="red" border-width="0.0mm" border-style="solid" vertical-align="bottom"  width="{@width}mm" height="{$height}mm" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{@icon}"/>
                              </fo:block>
                            </fo:table-cell>
                          </xsl:for-each>
                          <!-- Rest of the Technologies used -->
                          <fo:table-cell number-rows-spanned="2" vertical-align="middle" display-align="center" padding-right="1mm">
                            <fo:block  border-color="olive" border-width="0.0mm" border-style="solid">
                              <xsl:for-each select="$CareerData/Technologies/Technology">
                                <xsl:variable name="caption" select="@caption"/>
                                <xsl:if test="count($LayoutData/HighligtedTechs/Technology[@caption=$caption]) = 0">
                                  <fo:inline color="#303030">
                                    <xsl:choose>
                                      <xsl:when test="position() = count($CareerData/Technologies/Technology)"><xsl:value-of select="concat($caption, '.')"/></xsl:when>
                                      <xsl:otherwise><xsl:value-of select="concat($caption, ',','&#160;')"/></xsl:otherwise>
                                    </xsl:choose>
                                  </fo:inline>
                                </xsl:if>
                              </xsl:for-each>
                            </fo:block>
                          </fo:table-cell>
                        </fo:table-row>

                        <fo:table-row height="5mm"  >
                          <xsl:for-each select="HighligtedTechs/Technology">
                            <fo:table-cell>
                              <fo:block text-align="center" border-color="red" border-width="0.0mm" border-style="solid" color="#303030"><xsl:value-of select="@caption"/></fo:block>
                            </fo:table-cell>
                          </xsl:for-each>
                        </fo:table-row>
                      </fo:table-body>
                    </fo:table>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </xsl:with-param>
        </xsl:call-template>
      </fo:table-cell>
    </fo:table-row>
    <fo:table-row height="{$LayoutDoc/RoundedBox/@spaceBefore}mm">
      <fo:table-cell border-color="red" border-width="0.0mm" border-style="solid" >
        <fo:block></fo:block>
      </fo:table-cell>
    </fo:table-row>

  </xsl:template>

  <xsl:template match="Page/EducationalBackground" mode="Content">
    <fo:table-row height="{@height}mm">
      <fo:table-cell>
        <xsl:variable name="id" select="@id"/>
        <xsl:variable name="EducationData" select="$ResumeDoc/EducationalBackground/Education[@id=$id]"/>
        <xsl:variable name="LayoutData" select="."/>
        <xsl:variable name="fontSize">
          <xsl:choose>
            <xsl:when test="count($LayoutData/@fontSize) = 0">11pt</xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="$LayoutData/@fontSize"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:variable>        
        <xsl:comment>EducationalBackground Content</xsl:comment>
        <xsl:call-template name="RoundBoxContent">
          <xsl:with-param name="headerAlign">center</xsl:with-param>
          <xsl:with-param name="headerText" select="$LangDoc/Translation[@id='education']"/>
          <xsl:with-param name="content">
            <fo:block margin-top="5mm">
              <fo:list-block font-family="Arial" font-size="{$fontSize}" letter-spacing="0.2pt"  start-indent="9mm">
                <xsl:for-each select="$ResumeDoc/EducationalBackground/Education">
                  <fo:list-item>
                    <xsl:if test="count($LayoutData/@lineHeight) &gt; 0">
                      <xsl:attribute name="line-height">
                        <xsl:value-of select="$LayoutData/@lineHeight"/>
                      </xsl:attribute>
                    </xsl:if>
                    <fo:list-item-label>
                      <fo:block>•</fo:block>
                    </fo:list-item-label>
                    <fo:list-item-body start-indent="16mm">
                      <fo:block>
                        <xsl:value-of select="concat(text(), ', ',@field, ', ',@date)"/>
                      </fo:block>
                    </fo:list-item-body>
                  </fo:list-item>
                </xsl:for-each>
              </fo:list-block>
            </fo:block>
          </xsl:with-param>
        </xsl:call-template>
      </fo:table-cell>
    </fo:table-row>
    <fo:table-row height="{$LayoutDoc/RoundedBox/@spaceBefore}mm">
      <fo:table-cell border-color="red" border-width="0.0mm" border-style="solid" >
        <fo:block></fo:block>
      </fo:table-cell>
    </fo:table-row>

  </xsl:template>  

  <xsl:template match="CareerDetails/Page" mode="Content">
    <xsl:comment> Content Layer </xsl:comment>
    <fo:block-container absolute-position="absolute" >
      <fo:table table-layout="fixed" width="100%" >
        <fo:table-body>
          <xsl:apply-templates select="CareerDetail" mode="Content"/>
          <xsl:apply-templates select="EducationalBackground" mode="Content"/>
        </fo:table-body>
      </fo:table>
    </fo:block-container>
  </xsl:template>

  <xsl:template match="Screenshot" mode="Content">
    <fo:table-row>
      <fo:table-cell>
        <xsl:variable name="id" select="@id"/>
        <xsl:variable name="SCData" select="$ResumeDoc/Screenshots/Screenshot[@id=$id]"/>
        <xsl:variable name="LayoutData" select="."/>

        <xsl:comment>Screenshots Content</xsl:comment>
        <fo:block font-family="Cambria" font-size="13pt" font-weight="bold" color="#4F80BD" space-before="2mm"><xsl:value-of select="$id"/>&#160;-&#160;<xsl:value-of select="$SCData/."/></fo:block>
        <fo:block space-before="1mm">
          <xsl:variable name="height">
            <xsl:choose>
              <xsl:when test="count(@height) = 0">100%</xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="@height"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:variable>
          <fo:external-graphic border-color="red" border-width="0.0mm" border-style="solid" vertical-align="bottom"  width="100%" height="{$height}" content-width="scale-to-fit" content-height="scale-to-fit" src="{$ImgBase}{$SCData/@image}"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
    <fo:table-row height="{$LayoutDoc/RoundedBox/@spaceBefore}mm">
      <fo:table-cell border-color="red" border-width="0.0mm" border-style="solid" >
        <fo:block>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>    
  </xsl:template>

  <xsl:template match="Screenshots/Page" mode="Content">
    <xsl:comment> Content Layer </xsl:comment>
    <fo:block-container absolute-position="absolute" >
      <fo:table table-layout="fixed" width="100%" >
        <fo:table-body>
          <xsl:apply-templates select="Screenshot" mode="Content"/>
        </fo:table-body>
      </fo:table>
    </fo:block-container>
  </xsl:template>

  <xsl:template name="PageDetails">
    <xsl:for-each select="$LayoutDoc/CareerDetails/Page">
      <fo:block page-break-before="always"/>
      <xsl:apply-templates select="." mode="Background"/>
      <xsl:apply-templates select="." mode="Content"/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template name="Screenshots">
    <xsl:for-each select="$LayoutDoc/Screenshots/Page">
      <fo:block page-break-before="always"/>
      <xsl:apply-templates select="." mode="Content"/>
    </xsl:for-each>    
  </xsl:template>
  
  <xsl:template name="ActualLayout">
    <xsl:comment>Starts actual layout.</xsl:comment>
    <fo:page-sequence master-reference="first">
      <!-- header 
      <fo:static-content flow-name="xsl-region-before">
        <fo:block text-align="end"
              font-size="10pt"
              font-family="serif"
              line-height="14pt" >
          XML Recommendation - p. <fo:page-number/>
        </fo:block>
      </fo:static-content>
      -->
      <!-- Footer -->
      <fo:static-content flow-name="xsl-region-after">
        <fo:block text-align="center" font-size="11pt" font-family="Arial" color="gray" padding-top="0mm"><xsl:value-of select="$LangDoc/Translation[@id='page']"/> <fo:page-number/> / <fo:page-number-citation-last ref-id="last-page"/></fo:block>
      </fo:static-content>
      
      <fo:flow flow-name="xsl-region-body">
        <xsl:call-template name="PageMain"/>
        <fo:block page-break-before="always"/>
        <xsl:call-template name="PagePersonalDataSheet"/>
        
        <xsl:call-template name="PageDetails"/>
        
        <xsl:if test="$LayoutDoc/@includeScreenShots='true'">
          <xsl:call-template name="Screenshots"/>
        </xsl:if>
        
        <!-- Needed for getting the PageCount -->
        <fo:block id="last-page"/>
      </fo:flow>
    </fo:page-sequence>        
  </xsl:template>
  
  <xsl:template match="/">
    <xsl:comment> - - - - - - - - - - - - - - - - - </xsl:comment>
    <xsl:comment> - - ATLANTIS DESIGN FO-OUTPUT - - </xsl:comment>
    <xsl:comment> - - - - - - - - - - - - - - - - - </xsl:comment>
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <xsl:call-template name="MasterLayout"/>
      <xsl:call-template name="ActualLayout"/> 
    </fo:root>
  </xsl:template>
  
</xsl:stylesheet>
