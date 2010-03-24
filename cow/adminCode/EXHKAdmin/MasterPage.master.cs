using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class MasterPage : System.Web.UI.MasterPage
{
    protected void Page_Init(object sender, EventArgs e)
    {
        if (Session["LoginTime"] == null)
        {
            Response.Redirect("Login.aspx?to_url=" + Request.Url.PathAndQuery);
            return;
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        Response.Expires = 0;
        Response.ExpiresAbsolute = DateTime.MinValue;
    }
    protected void LinkButton1_Click(object sender, EventArgs e)
    {
        Session.Remove("LoginTime");
        Response.Redirect("/");
    }
}
