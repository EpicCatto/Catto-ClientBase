package catto.uwu.processor;

import catto.uwu.event.EventManager;
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
             new Reflections("catto").getSubTypesOf(Processor.class).forEach(clazz -> {
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

     public Processor getProcessor(final Class<? extends Processor> clazz) {
         return processors.stream().filter(processor -> processor.getClass().equals(clazz)).findFirst().orElse(null);
     }
}

