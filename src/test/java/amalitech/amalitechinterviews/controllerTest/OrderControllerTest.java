package amalitech.amalitechinterviews.controllerTest;

import amalitech.amalitechinterviews.controller.OrderController;
import amalitech.amalitechinterviews.request.OrderRequest;
import amalitech.amalitechinterviews.response.OrderResponse;
import amalitech.amalitechinterviews.service.OrderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @Disabled("Later work")
    void createOrder_ShouldCreateOrderWhenAuthenticated() throws Exception {
        // Arrange
        OrderRequest orderRequest = new OrderRequest();
        OrderResponse orderResponse = new OrderResponse();
        when(orderService.createOrder(any(OrderRequest.class), anyLong())).thenReturn(orderResponse);

        mockMvc.perform(post("/createOrder")
                        .contentType("application/json")
                        .content("{ \"input\": {\n" +
                                "  \"productId\": 1,\n" +
                                "  \"quantity\": 5\n" +
                                "}\n, \"userId\": 1 }")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(orderResponse.getId()));
    }
}

