<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="OrderList.aspx.cs" Inherits="OrderList" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">

    <script type="text/javascript">
        $(function() {
            $("#tabs").tabs('select', 1)
        });
 </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <table width="700px">
        <tr>
            <td>
                状态
            </td>
            <td>
                <asp:DropDownList ID="ddlStatus" runat="server" DataTextField="value" DataValueField="key">
                </asp:DropDownList>
            </td>
            <td>
                用户登录名
            </td>
            <td>
                <asp:TextBox ID="txtMemberLoginID" runat="server"></asp:TextBox>
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
        DataKeyNames="id" OnPageIndexChanging="GridView1_PageIndexChanging" OnRowCommand="GridView1_RowCommand">
        <RowStyle BackColor="#E3EAEB" />
        <Columns>
            <asp:TemplateField HeaderText="状态">
                <ItemTemplate>
                    <%# CommonHelper.GetOrderNameByID(Eval("status").ToString()) %>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="member_name" HeaderText="会员" SortExpression="member_name" />
            <asp:TemplateField HeaderText="会员">
                <ItemTemplate>
                    <asp:HyperLink ID="HyperLink2" runat="server" NavigateUrl='<%# Eval("id", "OrderDetail.aspx?id={0}") %>'
                        Text='<%# Eval("member_name").ToString() %>'></asp:HyperLink>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="total_price" HeaderText="总价" SortExpression="total_price" />
            <asp:BoundField DataField="pay_name" HeaderText="支付方式" SortExpression="pay_name" />
            <asp:BoundField DataField="delivery_name" HeaderText="送货方式" SortExpression="delivery_name" />
            <asp:BoundField DataField="order_time" HeaderText="下单时间" SortExpression="order_time" />
            <asp:BoundField DataField="memo" HeaderText="备注" SortExpression="memo" />
            <asp:TemplateField HeaderText="相关操作">
                <ItemTemplate>
                    <asp:LinkButton ID="LinkButton1" runat="server" CausesValidation="false" CommandName="PAID"
                        Text="确认付款" CommandArgument='<%# Eval("id") %>' OnClientClick="return confirm('确认置状态为已付款吗？');"></asp:LinkButton>
                    <asp:LinkButton ID="LinkButton2" runat="server" CausesValidation="false" CommandName="DELIVERED"
                        Text="确认发货" CommandArgument='<%# Eval("id") %>' OnClientClick="return confirm('确认置状态为已发货吗？');"></asp:LinkButton>
                    <asp:LinkButton ID="LinkButton3" runat="server" CausesValidation="false" CommandName="SUCCESS"
                        Text="成功完成" CommandArgument='<%# Eval("id") %>' OnClientClick="return confirm('确认置状态为成功完成吗？');"></asp:LinkButton>
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
