using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Login : System.Web.UI.Page
{
    protected void Page_Init(object sender, EventArgs e)
    {

#if DEBUG
            Session["LoginTime"] = DateTime.Now.ToString("yyyy年MM月dd日 星期ddd HH:mm");
            Response.Redirect("default.aspx");
#else
        if (Session["LoginTime"] != null)
        {
            if (string.IsNullOrEmpty(Request["to_url"]))
                Response.Redirect("default.aspx");
            else
                Response.Redirect(Request["to_url"]);
        }
#endif
    }

    protected void Page_Load(object sender, EventArgs e)
    {
    }

    protected void btnLogin_Click(object sender, EventArgs e)
    {

        if (txtName.Text != "admin" || txtPassword.Text != "123")
        {
            ClientScript.RegisterStartupScript(Page.GetType(), "error", "<script>alert('用户名密码错或不存在该用户');</script>");
            return;
        }


        Session["LoginTime"] = DateTime.Now.ToString("yyyy年MM月dd日 星期ddd HH:mm");
        /* Session["CorpID"] = dr["corp_id"].ToString();
         Session["LoginName"] = txtUserName.Text.Trim();
*/
        if (string.IsNullOrEmpty(Request["to_url"]))
            Response.Redirect("default.aspx");
        else
            Response.Redirect(Request["to_url"]);



    }
}
