<?php

namespace App\Controller;

use App\Entity\Gerant;
use App\Entity\Reservation;
use App\Form\GerantType;
use App\Form\ReservationType;
use App\Repository\GerantRepository;
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


class GerantController extends AbstractController
{



    #[Route('/indexx', name: 'app_indexx')]
    public function indexx(): Response
    {

        return $this->render('client/index.html.twig');
    }
    #[Route('/gerant', name: 'app_gerant')]
    public function index(): Response
    {
        return $this->render('admin.html.twig', [
            'controller_name' => 'GerantController',
        ]);
    }
    #[Route('/add_gerant', name: 'add_gerant')]

    public function Add(Request  $request , ManagerRegistry $doctrine ,SluggerInterface $slugger, SessionInterface $session) : Response {

        $Gerant =  new Gerant() ;
        $form =  $this->createForm(GerantType::class,$Gerant) ;
        $form->add('Ajouter' , SubmitType::class) ;
        $form->handleRequest($request) ;


         // Les lignes de censure
    $censor = new CensorWords;
    $langs = array('fr', 'it', 'en-us', 'en-uk', 'es');
    $badwords = $censor->setDictionary($langs);
    $censor->setReplaceChar("*");


        if($form->isSubmitted()&& $form->isValid()){
            $brochureFile = $form->get('image')->getData();
            
            $originalFilename = pathinfo($brochureFile->getClientOriginalName(), PATHINFO_FILENAME);
            //$uploads_directory = $this->getParameter('upload_directory');
            $safeFilename = $slugger->slug($originalFilename);
            $newFilename = $safeFilename.'-'.uniqid().'.'.$brochureFile->guessExtension();
            //$fileName = md5(uniqid()).'.'.$file->guessExtension();
            $brochureFile->move(
                $this->getParameter('upload_directory'),
                $newFilename
            );
            $Gerant->setImage(($newFilename));


              // Censure du contenu du Description
        $string = $censor->censorString($Gerant->getDescription());
        $Gerant->setDescription($string['clean']);


            
            $em= $doctrine->getManager() ;
            $em->persist($Gerant);
            $em->flush();
            $gerantName = $Gerant->getName();

        // Créer le message de notification
        $notificationMessage = "L'ajout a été effectué pour cet utilisateur, nom de la gerant : $gerantName";

        // Ajouter la notification à la session flash
        $session->getFlashBag()->add('success', $notificationMessage);

            return $this ->redirectToRoute('add_gerant') ;
        }
        return $this->render('gerant/addgerants.html.twig' , [
            'form' => $form->createView()
        ]) ;
    }

    #[Route('/afficher_gerant', name: 'afficher_gerant')]
   
public function AfficheGerant(GerantRepository $repo, PaginatorInterface $paginator, Request $request): Response
{
    $gerants=$repo->findAll() ;

    $pagination = $paginator->paginate(
        $gerants,
        $request->query->getInt('page', 1),
        1 // items per page
    );

    return $this->render('gerant/index.html.twig', [
        'Gerant' => $pagination,
        'ajoutA' => $gerants
    ]);
}

    #[Route('/delete_ab/{id}', name: 'delete_ab')]
    public function Delete($id,GerantRepository $repository , ManagerRegistry $doctrine) : Response {
        $Gerant=$repository->find($id) ;
        $em=$doctrine->getManager() ;
        $em->remove($Gerant);
        $em->flush();
        return $this->redirectToRoute("afficher_gerant") ;

    }
    #[Route('/update_ab/{id}', name: 'update_ab')]
    function update(GerantRepository $repo,$id,Request $request , ManagerRegistry $doctrine,SluggerInterface $slugger){
        $Gerant = $repo->find($id) ;
        $form=$this->createForm(GerantType::class,$Gerant) ;
        $form->add('update' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){
            $brochureFile = $form->get('image')->getData();
            
            $originalFilename = pathinfo($brochureFile->getClientOriginalName(), PATHINFO_FILENAME);
            
            $safeFilename = $slugger->slug($originalFilename);
            $newFilename = $safeFilename.'-'.uniqid().'.'.$brochureFile->guessExtension();
            
            $brochureFile->move(
                $this->getParameter('upload_directory'),
                $newFilename
            );
            $Gerant->setImage(($newFilename));

            $Gerant = $form->getData();
            $em=$doctrine->getManager() ;
            $em->flush();
            return $this ->redirectToRoute('afficher_gerant') ;
        }
        return $this->render('gerant/updategerants.html.twig' , [
            'form' => $form->createView()
        ]) ;

    }

 
    #[Route('/add_reservation/{id}', name: 'add_reservation')]
    
public function sendMessage(Request  $request , ManagerRegistry $doctrine): Response
 {
     $Reservation =  new Reservation() ;
        $form =  $this->createForm(ReservationType::class,$Reservation) ;
        $form->add('Ajouter' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){
            $Reservation = $form->getData();
            $em= $doctrine->getManager() ;
            $em->persist($Reservation);
            $em->flush();
            return $this ->redirectToRoute('add_reservation') ;
        }
        return $this->render('reservation/frontreserver.html.twig' , [
            'form' => $form->createView()
        ]) ;
 }
  

}


