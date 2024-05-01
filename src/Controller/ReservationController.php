<?php

namespace App\Controller;

use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\ReservationRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use App\Entity\Gerant;

use App\Form\GerantType;

use App\Repository\GerantRepository;

use Symfony\Component\String\Slugger\SluggerInterface;

use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Snipe\BanBuilder\CensorWords;
use Dompdf\Dompdf;
use Dompdf\Options;
class ReservationController extends AbstractController
{
    #[Route('/reservation', name: 'app_reservation')]
    public function index(): Response
    {
        return $this->render('base.html.twig', [
            'controller_name' => 'ReservationController',
        ]);
    }
    #[Route('/add_reservation', name: 'add_reservation')]

    public function Add(Request  $request , ManagerRegistry $doctrine ) : Response {
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
        return $this->render('reservation/frontadd.html.twig' , [
            'form' => $form->createView()
        ]) ;
    }

    #[Route('/afficher_reservation', name: 'afficher_reservation')]
    public function AfficheReservation (ReservationRepository $repo ,PaginatorInterface $paginator ,Request $request     ): Response
    {
        
        $Reservation=$repo->findAll() ;
        $pagination = $paginator->paginate(
            $Reservation,
            $request->query->getInt('page', 1),
            2 // items per page
        );
        return $this->render('reservation/index.html.twig' , [
            'Reservation' => $pagination ,
            'ajoutA' => $Reservation
        ]) ;
    }

    #[Route('/delete_adh/{id}', name: 'delete_adh')]
    public function Delete($id,ReservationRepository $repository , ManagerRegistry $doctrine) : Response {
        $Reservation=$repository->find($id) ;
        $em=$doctrine->getManager() ;
        $em->remove($Reservation);
        $em->flush();
        return $this->redirectToRoute("afficher_reservation") ;

    }
    #[Route('/update_adh/{id}', name: 'update_adh')]
    function update(ReservationRepository $repo,$id,Request $request , ManagerRegistry $doctrine){
        $Reservation = $repo->find($id) ;
        $form=$this->createForm(ReservationType::class,$Reservation) ;
        $form->add('update' , SubmitType::class) ;
        $form->handleRequest($request) ;
        if($form->isSubmitted()&& $form->isValid()){

            $Reservation = $form->getData();
            $em=$doctrine->getManager() ;
            $em->flush();
            return $this ->redirectToRoute('afficher_reservation') ;
        }
        return $this->render('reservation/update.html.twig' , [
            'form' => $form->createView()
        ]) ;

    }
    



   
}
