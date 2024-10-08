/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.dev.orderservice.query.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.dev.orderservice.query.entities.OrderEntity;

public interface OrdersRepo extends JpaRepository <OrderEntity, String>{
    
}
