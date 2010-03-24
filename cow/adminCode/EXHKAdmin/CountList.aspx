<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="CountList.aspx.cs" Inherits="CountList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">

    <script type="text/javascript">
        $(function() {
            $("#tabs").tabs('select', 2)
        });

        var controlName = '<%= hdfCountValue.ClientID %>';

        function ChangeCount(val) {
            rVal = prompt('请输入您需要当前的数值为', val);
            document.getElementById(controlName).value = rVal;
        }

    </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <table width="700px">
        <tr>
            <td>
                产品名称
            </td>
            <td>
                <asp:TextBox ID="txtProductName" runat="server"></asp:TextBox>
            </td>
            <td>
                <asp:Button ID="btnFilte" runat="server" Text=" 过 滤 " OnClick="btnFilte_Click" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                汇总：<asp:CheckBox ID="chkGroup" runat="server" Text="按产品汇总显示" />
            </td>
            <td>
            </td>
        </tr>
    </table>
    <asp:GridView ID="GridView1" runat="server" CellPadding="4" ForeColor="#333333" Width="780px"
        GridLines="None" AllowPaging="True" PageSize="20" AutoGenerateColumns="False"
        DataKeyNames="id" OnPageIndexChanging="GridView1_PageIndexChanging" OnRowCommand="GridView1_RowCommand">
        <RowStyle BackColor="#E3EAEB" />
        <Columns>
            <asp:TemplateField HeaderText="状态" Visible="false">
                <ItemTemplate>
                    <asp:HyperLink ID="HyperLink2" runat="server" NavigateUrl='<%# Eval("id", "#{0}") %>'
                        Text='<%# Eval("product_name") %>'></asp:HyperLink>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="product_name" HeaderText="产品名称" SortExpression="product_name" />
            <asp:BoundField DataField="color_name" HeaderText="颜色" SortExpression="total_price" />
            <asp:BoundField DataField="size" HeaderText="尺寸" SortExpression="member_name" />
            <asp:BoundField DataField="count" HeaderText="数量" SortExpression="pay_name" />
            <asp:TemplateField HeaderText="相关操作">
                <ItemTemplate>
                    <asp:LinkButton ID="LinkButton1" runat="server" CausesValidation="false" CommandName="CHANGE"
                        Text="修改数量" CommandArgument='<%# Eval("id") %>' OnClientClick='<%# String.Format("ChangeCount({0})", Eval("count")) %>'></asp:LinkButton>
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
    <asp:HiddenField ID="hdfCountValue" runat="server" />
</asp:Content>
