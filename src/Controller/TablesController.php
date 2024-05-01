<?php

namespace App\Controller;

use App\Entity\Tables;
use App\Entity\Reservation;
use App\Form\TablesType;
use App\Form\ReservationType;
use App\Repository\TablesRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Snipe\BanBuilder\CensorWords;


class TablesController extends AbstractController
{



    #[Route('/indexx', name: 'app_indexx')]
    public function indexx(): Response
    {

        return $this->render('client/index.html.twig');
    }
    #[Route('/tables', name: 'app_tables')]
    public function index(): Response
    {
        return $this->render('admin.html.twig', [
            'controller_name' => 'TablesController',
        ]);
    }
    #[Route('/add_tables', name: 'add_tables')]

    public function Add(Request  $request , ManagerRegistry $doctrine ,SluggerInterface $slugger, SessionInterface $session) : Response {

        $Tables =  new Tables() ;
        $form =  $this->createForm(TablesType::class,$Tables) ;
        $form->add('Ajouter' , SubmitType::class) ;
        $form->handleRequest($request) ;


        


        if($form->isSubmitted()&& $form->isValid()){
           
           


            
            $em= $doctrine->getManager() ;
            $em->persist($Tables);
            $em->flush();
            

        

            return $this ->redirectToRoute('add_tables') ;
        }
        return $this->render('tables/addtabless.html.twig' , [
            'form' => $form->createView()
        ]) ;
    }

    #[Route('/afficher_tables', name: 'afficher_tables')]
   
public function AfficheTables(TablesRepository $repo, PaginatorInterface $paginator, Request $request): Response
{
    $tabless=$repo->findAll() ;

    $pagination = $paginator->paginate(
        $tabless,
        $request->query->getInt('page', 1),
        1 // items per page
    );

    return $this->render('tables/index.html.twig', [
        'Tables' => $pagination,
        'ajoutA' => $tabless
    ]);
}

    #[Route('/delete_ab/{id}', name: 'delete_ab')]
    public function Delete($id,TablesRepository $repository , ManagerRegistry $doctrine) : Response {
        $Tables=$repository->find($id) ;
        $em=$doctrine->getManager() ;
        $em->remove($Tables);
        $em->flush();
        return $this->redirectToRoute("afficher_tables") ;

    }
    #[Route('/update_ab/{id}', name: 'update_ab')]
    function update(TablesRepository $repo,$id,Request $request , ManagerRegistry $doctrine,SluggerInterface $slugger){
        $Tables = $repo->find($id) ;
        $form=$this->createForm(TablesType::class,$Tables) ;
        $form->add('update' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){
            

            $Tables = $form->getData();
            $em=$doctrine->getManager() ;
            $em->flush();
            return $this ->redirectToRoute('afficher_tables') ;
        }
        return $this->render('tables/updatetabless.html.twig' , [
            'form' => $form->createView()
        ]) ;

    }

 
    
  

}


