using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using MySql.Data.MySqlClient;
using System.Configuration;

public partial class OrderDetail : System.Web.UI.Page
{
    protected void Page_Init(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            if (CommonHelper.CheckRequestId(Request["id"], "trade_order") == -1)
                Response.Redirect("OrderList.aspx");
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {

            ViewState["id"] = Request["id"];

            BindData();
        }

    }

    private void BindData()
    {
        string strSql = "select tro.*,COALESCE(m.name,'未注册用户') member_name,d.name delivery_name,p.name pay_name " +
            " from delivery d,payment p,(select * from trade_order where id= @id ) tro left join member m on tro.member_id=m.id where tro.delivery_id=d.id and tro.pay_id=p.id";
        DataRow dr = MySqlHelper.ExecuteDataRow(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql, new MySqlParameter("@id", (string)ViewState["id"]));
        txtStatus.Text = CommonHelper.GetOrderNameByID(dr["status"].ToString());
        txtMemberName.Text = dr["member_name"].ToString();
        txtTotalPrice.Text = dr["total_price"].ToString();
        txtPayName.Text = dr["pay_name"].ToString();
        txtDeliveryName.Text = dr["delivery_name"].ToString();
        txtMemo.Text = dr["memo"].ToString();
        txtName.Text = dr["name"].ToString();
        txtTelephone.Text = dr["telphone"].ToString();
        txtMobile.Text = dr["mobile"].ToString();
        txtCountry.Text = dr["country"].ToString();
        txtProvince.Text = dr["province"].ToString();
        txtCity.Text = dr["city"].ToString();
        txtArea.Text = dr["area"].ToString();
        txtAddress.Text = dr["address"].ToString();
        txtZipCode.Text = dr["zipcode"].ToString();

        //TODO 要根据目前状态，过滤掉不可去的状态
        ddlStatus.DataSource = CommonHelper.GetAllOrderIDandName();
        ddlStatus.DataBind();

        ddlStatus.SelectedValue = dr["status"].ToString();
    }

    protected void btnChangeStatus_Click(object sender, EventArgs e)
    {
        string strSql = "update trade_order set status = @status where id=@id";
        MySqlHelper.ExecuteNonQuery(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString,
            strSql, new MySqlParameter("@status", ddlStatus.SelectedValue), new MySqlParameter("@id", (string)ViewState["id"]));
        Response.Redirect("OrderList.aspx");
    }
}
