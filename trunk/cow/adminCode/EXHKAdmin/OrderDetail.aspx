<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="OrderDetail.aspx.cs" Inherits="OrderDetail" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="Server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <h1>
        订单</h1>
    <div>
        <h2>
            1.订单基本属性
        </h2>
        <table>
            <tr>
                <td>
                    状态
                </td>
                <td>
                    <asp:TextBox ID="txtStatus" runat="server"></asp:TextBox>
                </td>
                <td>
                    会员
                </td>
                <td>
                    <asp:TextBox ID="txtMemberName" runat="server"></asp:TextBox>
                </td>
                <td colspan="2">
                </td>
            </tr>
            <tr>
                <td>
                    总价
                </td>
                <td>
                    <asp:TextBox ID="txtTotalPrice" runat="server"></asp:TextBox>
                </td>
                <td>
                    支付方式
                </td>
                <td>
                    <asp:TextBox ID="txtPayName" runat="server"></asp:TextBox>
                </td>
                <td>
                    送货方式
                </td>
                <td>
                    <asp:TextBox ID="txtDeliveryName" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                    附注
                </td>
                <td colspan="5">
                    <asp:TextBox ID="txtMemo" runat="server" TextMode="MultiLine" Width="300px"></asp:TextBox>
                </td>
            </tr>
            <thead>
                <tr>
                    <td colspan="6">
                        收货人资料
                    </td>
                </tr>
            </thead>
            <tr>
                <td>
                    姓名
                </td>
                <td>
                    <asp:TextBox ID="txtName" runat="server"></asp:TextBox>
                </td>
                <td>
                    电话
                </td>
                <td>
                    <asp:TextBox ID="txtTelephone" runat="server"></asp:TextBox>
                </td>
                <td>
                    手机
                </td>
                <td>
                    <asp:TextBox ID="txtMobile" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                    国家
                </td>
                <td>
                    <asp:TextBox ID="txtCountry" runat="server"></asp:TextBox>
                </td>
                <td>
                    省份
                </td>
                <td>
                    <asp:TextBox ID="txtProvince" runat="server"></asp:TextBox>
                </td>
                <td>
                    城市
                </td>
                <td>
                    <asp:TextBox ID="txtCity" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                    地区
                </td>
                <td>
                    <asp:TextBox ID="txtArea" runat="server"></asp:TextBox>
                </td>
                <td>
                    邮编
                </td>
                <td>
                    <asp:TextBox ID="txtZipCode" runat="server"></asp:TextBox>
                </td>
                <td colspan="2">
                </td>
            </tr>
            <tr>
                <td>
                    详细地址
                </td>
                <td colspan="5">
                    <asp:TextBox ID="txtAddress" runat="server" TextMode="MultiLine" Width="300px"></asp:TextBox>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <h2>
            2.订单下的产品列表
        </h2>
        <asp:GridView ID="GridView1" runat="server" CellPadding="4" ForeColor="#333333" Width="780px"
            GridLines="None" AllowPaging="True" PageSize="20" DataSourceID="SqlDataSource1">
            <Columns>
                <asp:TemplateField HeaderText="产品名称">
                    <ItemTemplate>
                        <asp:HyperLink ID="HyperLink2" runat="server" NavigateUrl='<%# Eval("product_id", "http://www.aike.hk/aike/product/productDetail?id={0}") %>'
                            Text='<%# Eval("name").ToString() %>'></asp:HyperLink>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="color" HeaderText="颜色" SortExpression="color" />
                <asp:BoundField DataField="size" HeaderText="尺寸" SortExpression="size" />
                <asp:BoundField DataField="amount" HeaderText="数量" SortExpression="amount" />
                <asp:BoundField DataField="name" HeaderText="产品名称" SortExpression="name" />
                <asp:BoundField DataField="name" HeaderText="产品名称" SortExpression="name" />
            </Columns>
            <RowStyle BackColor="#E3EAEB" />
            <FooterStyle BackColor="#1C5E55" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="#666666" ForeColor="White" HorizontalAlign="Center" />
            <SelectedRowStyle BackColor="#C5BBAF" Font-Bold="True" ForeColor="#333333" />
            <HeaderStyle BackColor="#1C5E55" Font-Bold="True" ForeColor="White" />
            <EditRowStyle BackColor="#7C6F57" />
            <AlternatingRowStyle BackColor="White" />
        </asp:GridView>
        <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>"
            ProviderName="<%$ ConnectionStrings:ConnectionString.ProviderName %>" SelectCommand="SELECT id, product_id, name, color, size, amount, price FROM order_product WHERE order_id = @order_id ">
            <SelectParameters>
                <asp:QueryStringParameter Name="order_id" QueryStringField="id" Type="Int32" />
            </SelectParameters>
        </asp:SqlDataSource>
    </div>
    <p></p>
    <div>
        <h2>
            订单相关操作
        </h2>
        <asp:DropDownList ID="ddlStatus" runat="server" DataTextField="value" DataValueField="key">
        </asp:DropDownList>
        <asp:Button ID="btnChangeStatus" runat="server" Text="更新状态" OnClick="btnChangeStatus_Click" />
    </div>
</asp:Content>
