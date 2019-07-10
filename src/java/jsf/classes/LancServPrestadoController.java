package jsf.classes;

import entidade.LancServPrestado;
import jsf.classes.util.JsfUtil;
import jsf.classes.util.PaginationHelper;
import beans.sessions.LancServPrestadoFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "lancServPrestadoController")
@SessionScoped
public class LancServPrestadoController implements Serializable {

    private LancServPrestado current;
    private DataModel items = null;
    @EJB
    private beans.sessions.LancServPrestadoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public LancServPrestadoController() {
    }
    
    public void resetFail() {
        current = null;
    }

    public LancServPrestado getSelected() {
        if (current == null) {
            current = new LancServPrestado();
            current.setLancServPrestadoPK(new entidade.LancServPrestadoPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private LancServPrestadoFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(100000) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (LancServPrestado) getItemsList().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItemsList().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new LancServPrestado();
        current.setLancServPrestadoPK(new entidade.LancServPrestadoPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getLancServPrestadoPK().setCpfFucionario(current.getFuncionario().getCpf());
            current.getLancServPrestadoPK().setCdObra(current.getObra().getCdObra());
            current.getLancServPrestadoPK().setCdServico(current.getServico().getCdServico());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LancServPrestadoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (LancServPrestado) getItemsList().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItemsList().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getLancServPrestadoPK().setCpfFucionario(current.getFuncionario().getCpf());
            current.getLancServPrestadoPK().setCdObra(current.getObra().getCdObra());
            current.getLancServPrestadoPK().setCdServico(current.getServico().getCdServico());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LancServPrestadoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (LancServPrestado) getItemsList().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItemsList().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LancServPrestadoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {

            items = getPagination().createPageDataModel();

        return items;
    }
    
    public DataModel getItemsList() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = LancServPrestado.class)
    public static class LancServPrestadoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LancServPrestadoController controller = (LancServPrestadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lancServPrestadoController");
            return controller.ejbFacade.find(getKey(value));
        }

        entidade.LancServPrestadoPK getKey(String value) {
            entidade.LancServPrestadoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entidade.LancServPrestadoPK();
            key.setDtServico(java.sql.Date.valueOf(values[0]));
            key.setCpfFucionario(Long.parseLong(values[1]));
            key.setCdObra(Integer.parseInt(values[2]));
            key.setCdServico(Integer.parseInt(values[3]));
            return key;
        }

        String getStringKey(entidade.LancServPrestadoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getDtServico());
            sb.append(SEPARATOR);
            sb.append(value.getCpfFucionario());
            sb.append(SEPARATOR);
            sb.append(value.getCdObra());
            sb.append(SEPARATOR);
            sb.append(value.getCdServico());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof LancServPrestado) {
                LancServPrestado o = (LancServPrestado) object;
                return getStringKey(o.getLancServPrestadoPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + LancServPrestado.class.getName());
            }
        }

    }

}
