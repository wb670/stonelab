<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="CountAdd.aspx.cs" Inherits="CountAdd" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">

    <script type="text/javascript">
        $(function() {
            $("#tabs").tabs('select', 2)
        });
 </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <asp:MultiView ID="MultiView1" runat="server" ActiveViewIndex="0">
        <asp:View ID="viewSearch" runat="server">
            <table width="700px">
                <tr>
                    <td>
                        产品名称
                    </td>
                    <td>
                        <asp:TextBox ID="txtNameKey" runat="server"></asp:TextBox>
                    </td>
                    <td>
                        <asp:Button ID="btnFilte" runat="server" Text=" 搜 索 " OnClick="btnFilte_Click" />
                    </td>
                </tr>
            </table>
            <asp:GridView ID="GridView1" runat="server" CellPadding="4" ForeColor="#333333" Width="780px"
                GridLines="None" AutoGenerateColumns="False" DataKeyNames="id" OnSelectedIndexChanged="GridView1_SelectedIndexChanged">
                <RowStyle BackColor="#E3EAEB" />
                <Columns>
                    <asp:BoundField DataField="cname" HeaderText="类别名" SortExpression="cname" />
                    <asp:HyperLinkField DataNavigateUrlFields="id" DataNavigateUrlFormatString="http://www.aike.hk/aike/product/productDetail?id={0}"
                        DataTextField="name" HeaderText="产品名" />
                    <asp:BoundField DataField="view" HeaderText="点击数" SortExpression="view" />
                    <asp:BoundField DataField="cover_img_name" HeaderText="封面图片" SortExpression="cover_img_name" />
                    <asp:BoundField DataField="market_price" HeaderText="市场价" SortExpression="market_price" />
                    <asp:BoundField DataField="my_price" HeaderText="爱可价" SortExpression="my_price" />
                    <asp:TemplateField ShowHeader="False">
                        <ItemTemplate>
                            <asp:LinkButton ID="LinkButton1" runat="server" CausesValidation="False" CommandName="Select"
                                Text="选择"></asp:LinkButton>
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
        </asp:View>
        <asp:View ID="viewAdd" runat="server">
            <table width="700px">
                <tr>
                    <td>
                        <asp:ListBox ID="listColor" runat="server"></asp:ListBox>
                    </td>
                    <td>
                        <asp:ListBox ID="listSize" runat="server"></asp:ListBox>
                    </td>
                    <td>
                        <asp:Button ID="btnAdd" runat="server" Text=" 增 加 " OnClick="btnAdd_Click" />
                    </td>
                </tr>
            </table>
        </asp:View>
        <asp:View ID="viewSure" runat="server">
            <table width="700px">
                <tr>
                    <td>
                        <asp:Label ID="labColor" runat="server" Text="Label"></asp:Label>
                    </td>
                    <td>
                        <asp:Label ID="labSize" runat="server" Text="Label"></asp:Label>
                    </td>
                    <td>
                        <asp:TextBox ID="txtCount" runat="server"></asp:TextBox>
                    </td>
                    <td>
                        <asp:HiddenField ID="hdfColorId" runat="server" />
                        <asp:HiddenField ID="hdfProductName" runat="server" />
                        <asp:HiddenField ID="hdfOldID" runat="server" />
                        <asp:Button ID="btnSure" runat="server" Text=" 确 定 " OnClick="btnSure_Click" />
                        <asp:Button ID="btnCancel" runat="server" Text=" 返 回 " OnClick="btnCancel_Click" />
                    </td>
                </tr>
            </table>
        </asp:View>
    </asp:MultiView>
</asp:Content>
