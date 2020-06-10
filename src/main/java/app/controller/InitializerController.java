package app.controller;

import app.entity.Good;
import app.entity.Order;
import app.entity.User;
import app.page_path.PagePath;
import app.service.impl.GoodsServiceImpl;
import app.service.impl.OrderGoodsServiceImpl;
import app.service.impl.OrderServiceImpl;
import app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static app.controller.GoodsAddController.ORDER_ID;

@Controller
@RequestMapping("/")
public class InitializerController {
    public static final String USER_NAME = "name";
    public static final String USER_ID = "id";
    public static final String ALL_GOODS = "allGoodsList";
    private static final String BASKET = "order";
    private static final String TERM = "term";
    private List<Good> allGoodsList;

    private GoodsServiceImpl goodsService;
    private OrderGoodsServiceImpl orderGoodsService;
    private OrderServiceImpl orderService;
    private UserServiceImpl userService;

    @Autowired
    public InitializerController(GoodsServiceImpl goodsService, OrderGoodsServiceImpl orderGoodsService, OrderServiceImpl orderService, UserServiceImpl userService) {
        this.goodsService = goodsService;
        this.orderGoodsService = orderGoodsService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping()
    public ModelAndView redirectToFirstPage() {
        return new ModelAndView(PagePath.HOME_PAGE.getURL());
    }

    @PostMapping("/createUserServlet")
    public ModelAndView createUser(@RequestParam(USER_NAME) String userName, HttpServletRequest request) {
        allGoodsList = goodsService.getGoods();
        ModelAndView model;

        if ((userName.equals("")) || (request.getParameter(TERM) == null)) {
            model = new ModelAndView(PagePath.TERMS_ERROR.getURL());

        } else {
            User user = userService.getUser(userName);
            Order order = orderService.get(String.valueOf(user.getId()));
            addSessionAttributes(user, order, request);
            model = createView(user, order);
        }
        return model;
    }

    private ModelAndView createView(User user, Order order) {
        ModelAndView model = new ModelAndView(PagePath.ADD.getURL()).addObject(ALL_GOODS, allGoodsList);
        model.addAllObjects(Stream.of(new Object[][]{
                {USER_NAME, user.getLogin()},
                {BASKET, orderGoodsService.getOrderedGoods(order.getId())},
        }).collect(Collectors.toMap(data -> (String) data[0], data -> data[1])));
        return model;
    }

    private void addSessionAttributes(User user, Order order, HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.setAttribute(ALL_GOODS, allGoodsList);
        session.setAttribute(USER_ID, user.getId());
        session.setAttribute(ORDER_ID, order.getId());
        session.setAttribute(USER_NAME, user.getLogin());
    }
}