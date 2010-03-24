<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="ProductEdit.aspx.cs" Inherits="ProductEdit" ValidateRequest="false" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <h1>
        修改商品信息</h1>
    <div>
        <h2>
            1.商品基本属性</h2>
        <table>
            <tr>
                <td>
                    商品名称：
                </td>
                <td>
                    <asp:TextBox ID="txtName" runat="server" size="36" MaxLength="36"></asp:TextBox><span
                        class="note">不超过14个汉字（28个字符）</span>
                </td>
            </tr>
            <tr>
                <td>
                    市场价：
                </td>
                <td>
                    <asp:TextBox ID="txtMarketPrice" runat="server" size="10" MaxLength="10"></asp:TextBox><span>元</span>
                </td>
            </tr>
            <tr>
                <td>
                    爱可价：
                </td>
                <td>
                    <asp:TextBox ID="txtMyPrice" runat="server" size="10" MaxLength="10"></asp:TextBox><span>元</span>
                </td>
            </tr>
            <tr>
                <td>
                    商品分类：
                </td>
                <td>
                    <asp:DropDownList ID="ddlCatagoryG1" runat="server" DataSourceID="SqlDataSource1"
                        AutoPostBack="true" DataTextField="name" DataValueField="id" OnSelectedIndexChanged="ddlCatagoryG1_SelectedIndexChanged">
                    </asp:DropDownList>
                    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
                        ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="select id,name from category where depth=1">
                    </asp:SqlDataSource>
                    <asp:DropDownList ID="ddlCatagoryG2" runat="server" DataTextField="name" DataValueField="id">
                    </asp:DropDownList>
                </td>
            </tr>
            <tr>
                <td>
                    可选尺寸：
                </td>
                <td>
                    <asp:CheckBoxList ID="cblSize" runat="server" RepeatDirection="Horizontal" DataSourceID="SqlDataSource2"
                        DataTextField="description" DataValueField="name">
                    </asp:CheckBoxList>
                    <asp:SqlDataSource ID="SqlDataSource2" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
                        ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="select name,description from size">
                    </asp:SqlDataSource>
                    <span class="note">无尺寸的物品可不选</span>
                </td>
            </tr>
            <tr>
                <td>
                    产品属性：
                </td>
                <td>
                    <input type="button" value="" class="btn_add" onclick="addItem('attribute');return false" /><span
                        class="note">可添加多个产品属性</span>

                    <script type="text/javascript">
                        function addItem(item) {
                            $("#" + item).before("<tr>" + $("#" + item).html() + "</tr>");
                        }
                    </script>

                </td>
            </tr>
            <asp:Repeater ID="RepeaterAtt" runat="server">
                <ItemTemplate>
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            属性名：<input type="text" name="ak" size="10" maxlength="10" value='<%# Eval("name") %>' />&nbsp;
                            属性值：<input type="text" size="10" name="av" maxlength="10" value='<%# Eval("value") %>' />
                            <input type="button" value="" class="m_delete" onclick="$(this).parent().parent().remove();return false" />
                            <span class="note">属性值不超过50个汉字</span>
                        </td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
            <tr id="attribute" style="display: none;">
                <td>
                    &nbsp;
                </td>
                <td>
                    属性名：<input type="text" name="ak" size="10" maxlength="10" />&nbsp; 属性值：<input type="text"
                        size="10" name="av" maxlength="10" />
                    <input type="button" value="" class="m_delete" onclick="$(this).parent().parent().remove();return false" />
                    <span class="note">属性值不超过50个汉字</span>
                </td>
            </tr>
            <tr>
                <td>
                    洗涤方式：
                </td>
                <td>
                    <asp:CheckBoxList ID="cblWashing" runat="server" DataSourceID="SqlDataSource3" RepeatDirection="Horizontal"
                        DataTextField="name" DataValueField="id">
                    </asp:CheckBoxList>
                    <asp:SqlDataSource ID="SqlDataSource3" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
                        ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="SELECT id,name from washing">
                    </asp:SqlDataSource>
                </td>
            </tr>
            <tr>
                <td>
                    封面图片：
                </td>
                <td>
                    <asp:FileUpload ID="fuCoverImage" runat="server" /><asp:Image ID="imgCoverImage"
                        runat="server" CssClass="small_color_img" />
                </td>
            </tr>
        </table>
    </div>
    <div>
        <h2>
            2.商品颜色</h2>
        <table>
            <tr>
                <td>
                    可选颜色：
                </td>
                <td>
                    <input type="button" value="" class="btn_add" onclick="addItem('color');return false" /><span
                        class="note">可添加多个颜色，图片使用jpg或gif格式。</span>
                </td>
            </tr>
            <asp:Repeater ID="RepeaterColor" runat="server" DataSourceID="SqlDataSourceColor"
                OnItemCommand="RepeaterColor_ItemCommand">
                <ItemTemplate>
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            颜色名：<input name='colorName' type="text" size="10" maxlength="10" value='<%# Eval("color_name") %>'
                                readonly="readonly" />
                            &nbsp;&nbsp;图片上传：<input type="file" value="" name='colorFile<%# Eval("id") %>' />缩略图：<span><img
                                src='<%# Eval("img_name",CommonHelper.UploadPathUrl+"t_{0}")%>' alt="" class="small_color_img" /></span>
                            <asp:Button ID="Button2" runat="server" Text="" CommandName="del" class="m_delete"
                                CommandArgument='<%# Eval("id")+"|"+Eval("img_name") %>' OnClientClick="return confirm('确认删除颜色？将同时该颜色下所有产品及色卡图片。')" /><asp:HiddenField
                                    ID="HiddenFieldId" runat="server" Value='<%# Eval("id")+"|"+ Eval("color_name") +"|"+ Eval("img_name") %>' />
                        </td>
                    </tr>
                </ItemTemplate>
            </asp:Repeater>
            <asp:SqlDataSource ID="SqlDataSourceColor" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
                ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="SELECT id,img_name,color_name FROM product_color WHERE product_id  = @product_id ">
                <SelectParameters>
                    <asp:QueryStringParameter Name="product_id" QueryStringField="id" Type="Int32" />
                </SelectParameters>
            </asp:SqlDataSource>
            <tr id="color" style="display: none">
                <td>
                    &nbsp;
                </td>
                <td>
                    颜色名：<input name="colorName" type="text" size="10" maxlength="10" />
                    &nbsp;&nbsp;图片上传：<input type="file" value="" name="colorFile" />
                    <input type="button" value="" class="m_delete" onclick="$(this).parent().parent().remove();return false" />
                </td>
            </tr>
        </table>
    </div>
    <div>
        <h2>
            3.商品详细描述</h2>
        <table cellpadding="0" cellspacing="0">
            <tr>
                <td>

                    <script type="text/javascript" src="/js/tiny_mce/tiny_mce.js"></script>

                    <script type="text/javascript">
                        tinyMCE.init({
                            language: "zh",
                            mode: "textareas",
                            theme: "advanced",
                            mode: "textareas",
                            plugins: "safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups",
                            theme_advanced_buttons1: "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
                            theme_advanced_buttons2: "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,code,|,preview,|,forecolor,backcolor",
                            theme_advanced_buttons3: "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
                            theme_advanced_buttons4: "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
                            theme_advanced_toolbar_location: "top",
                            theme_advanced_toolbar_align: "left"
                        });
                    </script>

                    <asp:TextBox ID="txtDescription" runat="server" TextMode="MultiLine" Rows="20" Width="530px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="note">为保持产品详细页面效果的统一性，编辑器省去字体样式和字体颜色功能。字数不超过2000个汉字。</span>
                </td>
            </tr>
        </table>
        <div class="gt-right-col">
            <asp:Button ID="btnModify" runat="server" Text="" CssClass="m_btn_do" OnClick="btnModify_Click" />
            <input type="reset" value="" class="m_btn_reset" />
        </div>
    </div>
</asp:Content>
