package epiccatto.catto.processor;

import epiccatto.catto.event.EventManager;
import epiccatto.catto.utils.ChatUtil;
import org.reflections.Reflections;

import java.util.ArrayList;

public final class ProcessorManager {


    private final ArrayList<Processor> processors = new ArrayList<>();

     public ProcessorManager() {
//         ClassIndex.getSubclasses(Processor.class, Processor.class.getClassLoader()).forEach(clazz -> {
//             try {
//                 System.out.println("Registering processor: " + clazz.getSimpleName());
//                 processors.add(clazz.newInstance());
//             } catch (final Exception e) {
//                 e.printStackTrace();
//             }
//         });
         try {
             new Reflections("epiccatto").getSubTypesOf(Processor.class).forEach(clazz -> {
                 try {
                     System.out.println("Registering processor: " + clazz.getSimpleName());
                     processors.add(clazz.newInstance());
                 } catch (final Exception e) {
                     e.printStackTrace();
                 }
             });


         } catch (Exception e) {
             System.out.println("Error loading processors");
         }

         processors.forEach(EventManager::register);

         System.out.println("Registered " + processors.size() + " processors.");
     }
}

