package app.page_path;

/**
 * This {@link Enum} encapsulate paths used in application
 */
public enum PagePath {
    ADD("/pages/addItem"),
    PRINT_CHECK("/pages/printCheck"),
    HOME_PAGE("/pages/homePage"),
    COMPLETIVE_SERVLET("/complete"),
    TERMS_ERROR("/errors/termsError"),
    ERROR_PATH_404("/errors/notFoundError"),
    EMPTY_BASKET_ERROR("/errors/emptyBasketError");

    private final String url;

    PagePath(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
