<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="ColorImgAdd.aspx.cs" Inherits="ColorImgAdd" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <h2>
        商品名称：<asp:Label ID="labName" runat="server">迷你可爱爬衣</asp:Label> &nbsp;&nbsp;( 编号：<asp:Label ID="labId"
            runat="server" ForeColor="Beige">EX001</asp:Label> )</h2>
    <asp:Repeater ID="Repeater1" runat="server" DataSourceID="SqlDataSource1" OnItemCommand="Repeater1_ItemCommand"
        OnItemDataBound="Repeater1_ItemDataBound">
        <ItemTemplate>
            <div class="m_color_name_title">
                颜色名：<%# Eval("color_name")%><span>(<img src="<%# Eval("img_name",CommonHelper.UploadPathUrl+"t_{0}")%>"
                    alt="" class="small_color_img" />)</span>
                <input type="file" value="" name='file<%# Eval("id")%>' /><asp:Button ID="Button1"
                    runat="server" Text="" CommandName="add" CommandArgument='<%# Eval("id")%>' class="btn_add" />
                <asp:HiddenField ID="HiddenFieldImgGroupId" runat="server" Value='<%# Eval("img_group_id")%>' />
            </div>
            <div class="m_color_p" id="divImgs" runat="server">
                <asp:Repeater ID="Repeater2" runat="server" OnItemCommand="Repeater2_ItemCommand">
                    <HeaderTemplate>
                        <ul>
                    </HeaderTemplate>
                    <ItemTemplate>
                        <li class="m_delete02">
                            <img src='<%# Eval("img_name",CommonHelper.UploadPathUrl+"t_{0}")%>' alt="" /><asp:Button
                                ID="Button2" runat="server" Text="" CommandName="del" CommandArgument='<%# Eval("img_id")+"|"+Eval("img_name") %>'
                                class="m_delete" />
                        </li>
                    </ItemTemplate>
                    <FooterTemplate>
                        </ul>
                    </FooterTemplate>
                </asp:Repeater>
                <div class="clear">
                </div>
            </div>
        </ItemTemplate>
    </asp:Repeater>
    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
        ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="SELECT id,img_group_id,img_name,color_name FROM product_color WHERE product_id  = @product_id ">
        <SelectParameters>
            <asp:QueryStringParameter Name="product_id" QueryStringField="id" Type="Int32" />
        </SelectParameters>
    </asp:SqlDataSource>
</asp:Content>
