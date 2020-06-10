package app.controller;

import app.service.ServiceException;
import app.service.impl.OrderGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static app.controller.InitializerController.ALL_GOODS;
import static app.page_path.PagePath.*;
import static app.service.impl.OrderGoodsServiceImpl.PRICE;
import static app.service.impl.OrderGoodsServiceImpl.PRINT_GOODS;

@Controller
@RequestMapping({"/"})
public class GoodsAddController{
    private static final String ITEM = "good";
    private static final String EMPTY_ELEMENT = "--Choose item--";
    public static final String ORDER_ID = "Order id";
    public static final String BASKET = "order";
    public static final String NAME = "name";
    private static final String ADD_GOOD_URL = "/goodsAddServlet";
    private static final String COMPLETE_ORDER_URL = "/complete";

    private OrderGoodsServiceImpl service;

    @Autowired
    public GoodsAddController(OrderGoodsServiceImpl service) {
        this.service = service;
    }

    /**
     * Handles {@link HttpServlet} POST Method.
     * If the item has not been selected returns to the selection page. If selected - adds it to
     * the basket and returns to the selection page
     * @param request  the {@link HttpServletRequest} contains selected item as a parameter
     */
    @RequestMapping(value = ADD_GOOD_URL, method = RequestMethod.POST)
    protected ModelAndView addGood(final HttpServletRequest request, @RequestParam(ITEM) String item) {
        HttpSession session = request.getSession();
        putToBasket(item, session);
        return createView(session, ADD.getURL());
    }

    private ModelAndView createView(HttpSession session, String path) {
        ModelAndView model = new ModelAndView(path);
        model.addAllObjects(Stream.of(new Object[][]{
                {NAME, session.getAttribute(NAME)},
                {ALL_GOODS, session.getAttribute(ALL_GOODS)},
                {BASKET, session.getAttribute(BASKET)},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1])));
        return model;
    }

    /**
     * Handles {@link HttpServlet} GET Method. Redirect user if no goods were chosen after authentication
     * and order submitting
     */
    @RequestMapping(value = {ADD_GOOD_URL, COMPLETE_ORDER_URL}, method = RequestMethod.GET)
    public ModelAndView doGet() {
        return new ModelAndView(ADD.getURL());
    }

    /**
     * Add not empty goods to basket
     * @param chosenItem item for adding
     */
    public void putToBasket(final String chosenItem, HttpSession session) {
        long orderId = (long)session.getAttribute(ORDER_ID);
        if (!chosenItem.equals(EMPTY_ELEMENT)) {
            service.add(chosenItem, orderId);
        }
        session.setAttribute(BASKET, service.getOrderedGoods(orderId));
    }

    @RequestMapping(value = COMPLETE_ORDER_URL, method = RequestMethod.POST)
    protected ModelAndView completeOrder(final HttpServletRequest request, @RequestParam(ITEM) String item) throws ServiceException {
        HttpSession session = request.getSession();
        putToBasket(item, session);
        return printCheckView(session);
    }

    /**
     * Chooses path for redirection by transferred basket's content
     * @param session {@link HttpSession} current session
     * @return path to redirect
     */
    private ModelAndView printCheckView(HttpSession session) {
        ModelAndView model;
        long orderId = (long)session.getAttribute(ORDER_ID);
        Map<String, Integer> basket = (Map<String, Integer>)session.getAttribute(BASKET);
        if (basket.size() == 0) {
            model = new ModelAndView(EMPTY_BASKET_ERROR.getURL());

        } else {
            model = new ModelAndView(PRINT_CHECK.getURL()).addObject(PRINT_GOODS, service.createPresentationParams(basket));
            model.addAllObjects(Stream.of(new Object[][]{
                    {NAME, session.getAttribute(NAME)},
                    {PRICE, service.countPrice(orderId)},
            }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1])));
        }
        return model;
    }
}


