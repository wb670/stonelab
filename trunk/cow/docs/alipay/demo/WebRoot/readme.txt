����ʹ��˵��
1  indexҳ���Ǵ���֧��url,ʹ��ItemUrl����ƴ��һ��url��
   indexҳ��Ĳ����Ѿ��Ǳ�Ҫ���������Բ����޸ġ���������΢������
   ���Լ���վ�����Ӧ�ı�������ֵ����Ӧ�������漴�ɣ����磺
   String out_trade_no		= Now_Date.toString();	
   ����ͻ������ţ�ȡ��ϵͳʱ�䣬Now_date��߱�����
   
2  alipay_notify.jspΪ��֧��������֪ͨ����������post��Ϣ�����ҳ�档
   ���Զ�Ӧ��notify_url����������á�
   
3  alipay_return.jspΪ��֧��������֪ͨ����ieҳ����ת֪ͨ��ֻҪ֧���ɹ���
   ֧����ͨ��get��ʽ��ת�������ַ�����Ҵ��в��������ҳ�档
   
4 java����Ҫע��������������⣬һ��Ҫ������ȥ����filter,
  ע�⣺һ��Ҫ��web.xml�����ù�������ÿ����Ŀ�ж�������������������������ֱ�Ӵ�
webcontent�ļ����£�web-inf�ļ����µ�web.xml�ļ���
  ���Բο��������£�
   http://blog.csdn.net/lixinye0123/archive/2006/03/26/639402.aspx
  ���磺
  <filter>
		<filter-name>Set Character Encoding</filter-name>
		<filter-class>filters.SetCharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>GBK</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>Set Character Encoding</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
