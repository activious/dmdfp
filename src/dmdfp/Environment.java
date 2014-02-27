package dmdfp;

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
    private Path cloudSchemaPath;

    private String itemDescriptionStylesheet;

    @PostConstruct
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String rootPath = ctx.getExternalContext().getRealPath("/");

        cloudSchemaPath = Paths.get(rootPath + "/resources/cloud.xsd");
        itemDescriptionStylesheet = rootPath + "/resources/itemDescription.xsl";
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
