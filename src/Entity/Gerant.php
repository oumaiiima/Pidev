<?php

namespace App\Entity;

use App\Repository\GerantRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: GerantRepository::class)]
class Gerant
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

   


    #[ORM\Column(type: Types::DATE_MUTABLE)]
   
   
    #[Assert\GreaterThan("now", message: "La date doit être postérieure à aujourd'hui")]
    private ?\DateTimeInterface $date = null;
   
    

    #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    private ?string $description = null;

    

    

    #[ORM\OneToMany(mappedBy: 'gerants', targetEntity: Reservation::class, orphanRemoval: true)]
    private Collection $reservations;

     #[ORM\OneToMany(mappedBy: 'gerantss', targetEntity: Tables::class, orphanRemoval: true)]
    private Collection $tables;
    #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    private ?string $Name = null;

     #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    private ?string $document = null; #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    private ?string $email = null; #[ORM\Column(length: 255)]
    #[Assert\NotNull (message: "Il faut remplire ce chemp")]
    private ?string $password = null;
    #[ORM\Column(length: 255)]
    private ?string $image = null;

    public function __construct()
    {
        $this->reservations = new ArrayCollection();
        $this->tables = new ArrayCollection();
    }

   
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }
    
    

    

    

   

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }



    
    /**
     * @return Collection<int, Reservation>
     */
    public function getReservations(): Collection
    {
        return $this->reservations;
    }

    public function addCReservationontrat( $reservation): self
    {
        if (!$this->reservations->contains($reservation)) {
            $this->reservations->add($reservation);
            $reservation->setGerants($this);
        }

        return $this;
    }

    public function removeCReservationontrat( $reservation): self
    {
        if ($this->reservations->removeElement($reservation)) {
            // set the owning side to null (unless already changed)
            if ($reservation->getGerants() === $this) {
                $reservation->setGerants(null);
            }
        }

        return $this;
    }

    public function getName(): ?string
    {
        return $this->Name;
    }

    public function setName(string $Name): self
    {
        $this->Name = $Name;

        return $this;
    }
    public function getDocument(): ?string
    {
        return $this->document;
    }

    public function setDocument(string $document): self
    {
        $this->document = $document;

        return $this;
    }
    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function getTables(): Collection
    {
        return $this->tables;
    }

    public function addCTablesontrat( $table): self
    {
        if (!$this->tables->contains($table)) {
            $this->tables->add($table);
            $table->setGerants($this);
        }

        return $this;
    }

    public function removeCTableontrat( $table): self
    {
        if ($this->tables->removeElement($table)) {
            // set the owning side to null (unless already changed)
            if ($table->getGerants() === $this) {
                $table->setGerants(null);
            }
        }

        return $this;
    }
    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }
    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }
    
}
