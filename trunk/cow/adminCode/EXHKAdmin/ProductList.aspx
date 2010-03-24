<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="ProductList.aspx.cs" Inherits="ProductList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <table width="700px">
        <tr>
            <td>
                主类别
            </td>
            <td>
                <asp:DropDownList ID="ddlCatagoryG1" runat="server" DataSourceID="SqlDataSource1"
                    AutoPostBack="true" DataTextField="name" DataValueField="id" OnSelectedIndexChanged="ddlCatagoryG1_SelectedIndexChanged">
                </asp:DropDownList>
                <asp:DropDownList ID="ddlCatagoryG2" runat="server" DataTextField="name" DataValueField="id">
                </asp:DropDownList>
                <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
                    ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="select -1 as id,'所有' as name union all select id,name from category where depth=1">
                </asp:SqlDataSource>
            </td>
            <td>
                品名关键字
            </td>
            <td>
                <asp:TextBox ID="txtNameKey" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <asp:Button ID="btnFilte" runat="server" Text=" 过 滤 " OnClick="btnFilte_Click" />
            </td>
        </tr>
    </table>
    <asp:GridView ID="GridView1" runat="server" CellPadding="4" ForeColor="#333333" Width="780px"
        GridLines="None" AllowPaging="True" PageSize="20" AutoGenerateColumns="False"
        DataKeyNames="id" OnPageIndexChanging="GridView1_PageIndexChanging" OnRowDeleting="GridView1_RowDeleting">
        <RowStyle BackColor="#E3EAEB" />
        <Columns>
            <asp:BoundField DataField="cname" HeaderText="类别名" SortExpression="cname" />
            <asp:HyperLinkField DataNavigateUrlFields="id" DataNavigateUrlFormatString="http://www.aike.hk/aike/product/productDetail?id={0}"
                DataTextField="name" HeaderText="产品名" />
            <asp:BoundField DataField="view" HeaderText="点击数" SortExpression="view" />
            <asp:BoundField DataField="cover_img_name" HeaderText="封面图片" SortExpression="cover_img_name" />
            <asp:BoundField DataField="market_price" HeaderText="市场价" SortExpression="market_price" />
            <asp:BoundField DataField="my_price" HeaderText="爱可价" SortExpression="my_price" />
            <asp:TemplateField HeaderText="相关操作">
                <ItemTemplate>
                    <asp:HyperLink ID="HyperLink1" runat="server" 
                        NavigateUrl='<%# Eval("id", "ProductEdit.aspx?id={0}") %>' Text="修改"></asp:HyperLink>
                    <asp:LinkButton ID="LinkButton1" runat="server" CausesValidation="False" CommandName="Delete"
                        Text="删除" OnClientClick="return confirm('确认要删除该产品数据吗？');"></asp:LinkButton>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <FooterStyle BackColor="#1C5E55" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="#666666" ForeColor="White" HorizontalAlign="Center" />
        <SelectedRowStyle BackColor="#C5BBAF" Font-Bold="True" ForeColor="#333333" />
        <HeaderStyle BackColor="#1C5E55" Font-Bold="True" ForeColor="White" />
        <EditRowStyle BackColor="#7C6F57" />
        <AlternatingRowStyle BackColor="White" />
    </asp:GridView>
</asp:Content>
