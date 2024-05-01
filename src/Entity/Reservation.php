<?php

namespace App\Entity;

use App\Repository\ReservationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\GerantRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;


#[ORM\Entity(repositoryClass: ReservationRepository::class)]
class Reservation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
   #[Assert\GreaterThan("now", message: "La date doit être postérieure à aujourd'hui")]
    public ?\DateTimeInterface $date_reservation = null;


  

    #[ORM\ManyToOne(inversedBy: 'reservations')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Gerant $gerants = null;


     #[ORM\ManyToOne(inversedBy: 'reservationss')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Tables $tables = null;





   
    
      

     

    public function getId(): ?int
    {
        return $this->id;
    }

    

    

     

    public function getGerants(): ?gerant
    {
        return $this->gerants;
    }

    public function setGerants(?gerant $s): self
    {
        $this->gerants = $s;

        return $this;
   }
   public function getTables(): ?tables
    {
        return $this->tables;
    }

    public function setTables(?tables $s): self
    {
        $this->tables = $s;

        return $this;
   }
   public function getDate_reservation(): ?\DateTimeInterface
    {
        return $this->date_reservation;
    }

    public function setDate_reservation(\DateTimeInterface $date_reservation): self
    {
        $this->date_reservation = $date_reservation;

        return $this;
    }

   



   
   
}
