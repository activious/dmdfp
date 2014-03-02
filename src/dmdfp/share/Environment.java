package dmdfp.share;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by khk on 2/27/14.
 */
@ManagedBean(name="env")
@ApplicationScoped
public class Environment implements Serializable
{
    private static final String
            RESOURCES = "/resources",
            CLOUD_SCHEMA = "/cloud.xsd",
            ITEM_DESC_STYLE = "/itemDescription.xsl";

    private Path cloudSchemaPath;
    private String itemDescriptionStylesheet;

    /**
     * JSF construct
     */
    public Environment() {}

    /**
     * JSF init
     */
    @PostConstruct
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        setBasePath(ctx.getExternalContext().getRealPath("/"));
    }

    public Environment(String basePath)
    {
        setBasePath(basePath);
    }

    private void setBasePath(String basePath)
    {
        cloudSchemaPath = Paths.get(basePath + RESOURCES + CLOUD_SCHEMA);
        itemDescriptionStylesheet = basePath + RESOURCES + ITEM_DESC_STYLE;
    }

    public Path getCloudSchemaPath()
    {
        return cloudSchemaPath;
    }

    public String getItemDescriptionStylesheet()
    {
        return itemDescriptionStylesheet;
    }
}
