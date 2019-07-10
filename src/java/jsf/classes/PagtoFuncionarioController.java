package jsf.classes;

import entidade.PagtoFuncionario;
import jsf.classes.util.JsfUtil;
import jsf.classes.util.PaginationHelper;
import beans.sessions.PagtoFuncionarioFacade;

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

@ManagedBean(name = "pagtoFuncionarioController")
@SessionScoped
public class PagtoFuncionarioController implements Serializable {

    private PagtoFuncionario current;
    private DataModel items = null;
    @EJB
    private beans.sessions.PagtoFuncionarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public PagtoFuncionarioController() {
    }
    
    public void resetFail() {
        current = null;
    }

    public PagtoFuncionario getSelected() {
        if (current == null) {
            current = new PagtoFuncionario();
            current.setPagtoFuncionarioPK(new entidade.PagtoFuncionarioPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private PagtoFuncionarioFacade getFacade() {
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
        current = (PagtoFuncionario) getItemsList().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItemsList().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PagtoFuncionario();
        current.setPagtoFuncionarioPK(new entidade.PagtoFuncionarioPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getPagtoFuncionarioPK().setCpfFuncionario(current.getFuncionario().getCpf());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagtoFuncionarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PagtoFuncionario) getItemsList().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItemsList().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getPagtoFuncionarioPK().setCpfFuncionario(current.getFuncionario().getCpf());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagtoFuncionarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (PagtoFuncionario) getItemsList().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PagtoFuncionarioDeleted"));
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

    @FacesConverter(forClass = PagtoFuncionario.class)
    public static class PagtoFuncionarioControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PagtoFuncionarioController controller = (PagtoFuncionarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "pagtoFuncionarioController");
            return controller.ejbFacade.find(getKey(value));
        }

        entidade.PagtoFuncionarioPK getKey(String value) {
            entidade.PagtoFuncionarioPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entidade.PagtoFuncionarioPK();
            key.setCpfFuncionario(Long.parseLong(values[0]));
            key.setDtInicio(java.sql.Date.valueOf(values[1]));
            return key;
        }

        String getStringKey(entidade.PagtoFuncionarioPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getCpfFuncionario());
            sb.append(SEPARATOR);
            sb.append(value.getDtInicio());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PagtoFuncionario) {
                PagtoFuncionario o = (PagtoFuncionario) object;
                return getStringKey(o.getPagtoFuncionarioPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PagtoFuncionario.class.getName());
            }
        }

    }

}
