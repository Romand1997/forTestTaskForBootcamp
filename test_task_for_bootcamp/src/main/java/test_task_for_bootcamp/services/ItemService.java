package test_task_for_bootcamp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task_for_bootcamp.model.Category;
import test_task_for_bootcamp.model.Item;
import test_task_for_bootcamp.model.User;
import test_task_for_bootcamp.repositories.CategoryRepository;
import test_task_for_bootcamp.repositories.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;
    private static final Logger logger = Logger.getLogger(ItemService.class.getName());
    public List<Item> getAllItems() {
        return itemRepository.findAllByIsActiveOrderByNameAsc(true);
    }
    public List<Item> getAllItemsByCategoryId(Long id){
        Category category = categoryRepository.findById(id).orElse(null);
        return itemRepository.findAllByIsActiveAndCategoryOrderByNameAsc(true, category);
    }

    public boolean placeABet(Long id, double price) {

        boolean flag = false;
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null) {
            if (item.getIsActive()) {
                if (item.getTimeOfLastBet()!=null) {
                    TimerTask task = new TimerTask() {
                        public void run() {
                            item.setIsActive(false);
                            itemRepository.save(item);
                            logger.info("Ваш товар купили");
                            logger.info("Вы купили товар");
                        }
                    };
                    Timer timer = new Timer("new Timer");
                    long delay = 60000L*item.getTimeOfSale();
                    timer.schedule(task, delay);
                }
                if (item.getPrice() == price) {
                    item.setPrice(item.getPrice() + 5);
                    item.setTimeOfLastBet(LocalDateTime.now());
                    if (item.getLastBuyer()!=null){
                        logger.info("Вашу ставку перебили");
                    }
                    item.setLastBuyer(userService.getUser());
                    itemRepository.save(item);
                    flag = true;
                }
            }
        }
        return flag;
    }

    public void addItem(Item item) {
        item.setIsActive(true);
        User user = userService.getUser();
        item.setAdOwner(user);
        itemRepository.save(item);
    }
}
