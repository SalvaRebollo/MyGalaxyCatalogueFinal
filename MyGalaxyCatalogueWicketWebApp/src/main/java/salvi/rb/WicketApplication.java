package salvi.rb;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import salvi.rb.myPages.*;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see salvi.rb.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return Inicio.class;
	}
	//public Class<? extends WebPage> getHomePage()
	//	{
	//		return HomePage.class;
	//	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// Get DEBUG INFORMATION
		getDebugSettings().setOutputComponentPath(true);
		getDebugSettings().setOutputMarkupContainerClassName(true);

		// add your configuration here
		// { === ROUTING === }
		//mountPage("homePage", HomePage.class);
		//
		//mountPage("oldPublicMainProducts", OldPublicMainProducts.class);
		//
		//mountPage("publicMainProducts", PublicMainProducts.class);
		//
		mountPage("myGalaxyCatalogueOld", AllTheProductsWebPage.class);
		//
		mountPage("myGalaxyCatalogue", MasonryView.class);
		//
		mountPage("statistics", Statistics.class);
		//
		mountPage("inicio", Inicio.class);
		//
		mountPage("faq", Faq.class);
	}
}
